/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.kaoqin.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.kaoqin.entity.AomKaoqin;

/**
 * 考勤信息DAO接口
 * @author fangzr
 * @version 2015-07-15
 */
@MyBatisDao
public interface AomKaoqinDao extends CrudDao<AomKaoqin> {
	
}