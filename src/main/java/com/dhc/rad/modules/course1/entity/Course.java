/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.course1.entity;

import org.hibernate.validator.constraints.Length;

import com.dhc.rad.common.persistence.DataEntity;

/**
 * 课程1生成Entity
 * @author fangzr
 * @version 2015-08-14
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 课程代码
	private String name;		// 课程名称
	private String xf;		// 学分
	private String nature;		// 课程性质编号
	
	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}

	@Length(min=1, max=64, message="课程代码长度必须介于 1 和 64 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=100, message="课程名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=5, message="学分长度必须介于 1 和 5 之间")
	public String getXf() {
		return xf;
	}

	public void setXf(String xf) {
		this.xf = xf;
	}
	
	@Length(min=1, max=64, message="课程性质编号长度必须介于 1 和 64 之间")
	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}
	
}