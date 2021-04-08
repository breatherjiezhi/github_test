package com.dhc.rad.common.quartz.dao;
import java.util.List;

import org.quartz.SchedulerException;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.common.quartz.entity.ScheduleJob;

/**
 * 定时任务 dao
 *
 * @author hj
 */
@MyBatisDao
public interface ScheduleJobDao extends CrudDao<ScheduleJob> {
	


	/*
	 * 删除
	 */
	public void deleteByHouseCode(String scheduleJobId);

	/*判断库房是否在其他地方被使用*/
	public List<ScheduleJob> CheckUsedByOther(String scheduleJobId);

	
	// 查询所有任务列表
	public List<ScheduleJob> queryList();
	
	
	

	public List<ScheduleJob> getAll();

	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void insertSelective(ScheduleJob job);

	/**
	 * 从数据库中查询job
	 */
	public ScheduleJob selectByJobId(Long jobId);


	/**
	 * 更改任务状态
	 * 
	 * @throws SchedulerException
	 */
	public void updateJob(ScheduleJob job);
}
