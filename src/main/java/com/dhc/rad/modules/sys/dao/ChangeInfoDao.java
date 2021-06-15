package com.dhc.rad.modules.sys.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.sys.entity.ChangeInfo;

import java.util.List;

@MyBatisDao
public interface ChangeInfoDao  extends CrudDao<ChangeInfo> {
   List<ChangeInfo> findApplyList(ChangeInfo  changeInfo);
}
