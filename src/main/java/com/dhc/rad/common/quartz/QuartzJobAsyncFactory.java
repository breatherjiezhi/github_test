package com.dhc.rad.common.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhc.rad.common.quartz.entity.ScheduleJob;

/**
 * 同步的任务工厂类
 *
 * Created by hj on 12/19/14.
 */
public class QuartzJobAsyncFactory implements Job {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobAsyncFactory.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

//        LOG.info("JobSyncFactory execute  --  异步执行");

        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
//        LOG.info("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get("scheduleJob");
//        LOG.info("任务名称 = [\" + scheduleJob.getJobName() + \"]");
        TaskUtils.invokMethod(scheduleJob);
    }
}
