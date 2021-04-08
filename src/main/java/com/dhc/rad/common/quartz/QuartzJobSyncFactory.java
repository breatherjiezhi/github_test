package com.dhc.rad.common.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhc.rad.common.quartz.entity.ScheduleJob;

/**
 * 任务工厂类,非同步(异步)
 *
 * User: hj
 * Date: 16-12-22
 * Time: 上午10:11
 */
@DisallowConcurrentExecution
public class QuartzJobSyncFactory implements Job {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobSyncFactory.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

//        LOG.info("JobFactory execute   ----   同步执行");
//        LOG.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
//        LOG.info("任务名称 = [" + scheduleJob.getJobName() + "]");
        TaskUtils.invokMethod(scheduleJob);
    }
}
