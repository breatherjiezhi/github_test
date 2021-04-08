package com.dhc.rad.common.quartz.entity;

/**
 * Created by hj on 2016/12/12.
 * Oh,today is a special day! ha ha
 * 
 * 根据需求，自行添加其他常量
 */
public enum GlobalConstant {
	//台账流水回写状态     00:成功  01：失败
	Success("00"), Failure("01"),
	
	
	//定时任务 相关
	STATUS_RUNNING ("1"),  				// 执行
	STATUS_NOT_RUNNING ("0"),			// 不执行
	CONCURRENT_IS ("1"), 				// 任务有状态
	CONCURRENT_NOT ("0");				// 任务无状态
	
	
	
	
	//构造器
	private GlobalConstant(String value) {
		this.value = value;
	}
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
