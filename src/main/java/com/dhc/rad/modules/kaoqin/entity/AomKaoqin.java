/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.kaoqin.entity;

import org.hibernate.validator.constraints.Length;

import com.dhc.rad.common.persistence.DataEntity;

/**
 * 考勤信息Entity
 * @author fangzr
 * @version 2015-07-15
 */
public class AomKaoqin extends DataEntity<AomKaoqin> {
	
	private static final long serialVersionUID = 1L;
	private String branch;		// 部门
	private String kepperby;		// 考勤员
	private String year;		// 年
	private String month;		// 月
	
	public AomKaoqin() {
		super();
	}

	public AomKaoqin(String id){
		super(id);
	}

	@Length(min=1, max=30, message="部门长度必须介于 1 和 30 之间")
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	@Length(min=1, max=30, message="考勤员长度必须介于 1 和 30 之间")
	public String getKepperby() {
		return kepperby;
	}

	public void setKepperby(String kepperby) {
		this.kepperby = kepperby;
	}
	
	@Length(min=1, max=4, message="年长度必须介于 1 和 4 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=1, max=2, message="月长度必须介于 1 和 2 之间")
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}