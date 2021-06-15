/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.dao;

import java.util.List;

import com.dhc.rad.common.persistence.TreeDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Param;

/**
 * 机构DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	List<Office> getCompanys(Office office);

	List<Office> findAllOffice();

	List<Office> 	findOffice(@Param("parentId") String parentId);

	List<Office> findRestaurantOffice();


	List<Office> findServiceUnit();
	
}
