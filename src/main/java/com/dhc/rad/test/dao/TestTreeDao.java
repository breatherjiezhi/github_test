/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.test.dao;

import com.dhc.rad.common.persistence.TreeDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author DHC
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}