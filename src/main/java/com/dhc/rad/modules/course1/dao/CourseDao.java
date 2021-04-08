/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.course1.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.course1.entity.Course;

/**
 * 课程1生成DAO接口
 * @author fangzr
 * @version 2015-08-14
 */
@MyBatisDao
public interface CourseDao extends CrudDao<Course> {
	
}