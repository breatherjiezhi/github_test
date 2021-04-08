/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.dao;



import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.gen.entity.GenScheme;

import java.util.List;

/**
 * 机构DAO接口
 * @author ZN
 * @version 2014-05-16
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {

    List<String> findNameList(GenScheme entity);
}
