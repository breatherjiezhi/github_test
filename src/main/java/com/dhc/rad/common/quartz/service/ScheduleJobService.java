package com.dhc.rad.common.quartz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.quartz.QuartzJobAsyncFactory;
import com.dhc.rad.common.quartz.QuartzJobSyncFactory;
import com.dhc.rad.common.quartz.dao.ScheduleJobDao;
import com.dhc.rad.common.quartz.entity.GlobalConstant;
import com.dhc.rad.common.quartz.entity.ScheduleJob;
import com.dhc.rad.common.service.BaseService;


@Service
public class ScheduleJobService extends BaseService implements InitializingBean{
	
	public final Logger log = Logger.getLogger(this.getClass());
	
	/** 调度工厂Bean */
//    @Autowired
//    private Scheduler scheduler;
	
    @Autowired
    private ScheduleJobDao scheduleJobDao;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
    
    
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	/*
	 * list页面分页
	 */
	public Page<ScheduleJob> findStore(Page<ScheduleJob> page, ScheduleJob entity) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		entity.getSqlMap().put("dsf", dataScopeFilter(entity.getCurrentUser(), "o", "a"));
		// 设置分页参数
		entity.setPage(page);
		// 执行分页查询
		page.setList(scheduleJobDao.findList(entity));
		
		return page;
	}
	

	/*保存*/
	public int insert(ScheduleJob scheduleJob) {
		scheduleJob.preInsert();
		return scheduleJobDao.insert(scheduleJob);
	}

	/*编辑*/
	public int update(ScheduleJob scheduleJob) {

		scheduleJob.preUpdate();
		return scheduleJobDao.update(scheduleJob);
	}

	/*删除       逻辑删除*/
	@Transactional(readOnly = false)
	public void doDeleteUser(List<String> list) {
		Iterator<String> it=list.iterator();
		while(it.hasNext()){
			String scheduleJobId=it.next();
			
			scheduleJobDao.deleteByHouseCode(scheduleJobId);
		}
	}

	

	/**
	 * 从数据库中取所有任务, 区别于getAllJob(获取计划执行的任务)
	 * 
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobDao.getAll();
	}

	/**
	 * 添加到数据库中,新增任务, 区别于addJob
	 * 
	 */
	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		scheduleJobDao.insertSelective(job);
	}

	/**
	 * 根据ID从数据库中查询job
	 */
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobDao.selectByJobId(jobId);
	}

	/**
	 * 更改任务状态
	 * 
	 * @throws SchedulerException
	 */
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(GlobalConstant.STATUS_NOT_RUNNING.getValue());
		} else if ("start".equals(cmd)) {
			job.setJobStatus(GlobalConstant.STATUS_RUNNING.getValue());
			addJob(job);
		}
		scheduleJobDao.updateJob(job);
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		if (GlobalConstant.STATUS_RUNNING.getValue().equals(job.getJobStatus())) {
			updateJobCron(job);
		}
		scheduleJobDao.updateJob(job);

	}

	// 系统启动之后，调用初始化方法，添加任务
		@PostConstruct
		public void init() throws Exception {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			// 这里获取任务信息数据
			List<ScheduleJob> jobList = scheduleJobDao.getAll();

			for (ScheduleJob job : jobList) {
				addJob(job);
			}
		}
		
		
	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !GlobalConstant.STATUS_RUNNING.getValue().equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".................添加任务...................");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = GlobalConstant.CONCURRENT_IS.getValue().equals(job.getIsConcurrent()) ? QuartzJobAsyncFactory.class : QuartzJobSyncFactory.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
			
			scheduleJobDao.update(job);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	
	

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
		scheduleJob.setCurrentStatus("01");
		scheduleJobDao.update(scheduleJob);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
		scheduleJob.setCurrentStatus("00");
		scheduleJobDao.update(scheduleJob);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
		scheduleJob.setCurrentStatus("02");
		scheduleJobDao.update(scheduleJob);
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	public static void main(String[] args) {
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("xxxxx");
	}
	
	
}
