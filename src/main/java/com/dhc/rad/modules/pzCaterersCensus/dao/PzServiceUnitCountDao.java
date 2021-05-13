package com.dhc.rad.modules.pzCaterersCensus.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzServiceUnitCount;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;

import java.util.List;

@MyBatisDao
public interface PzServiceUnitCountDao extends CrudDao<PzServiceUnitCount> {
     List<PzServiceUnitCount> selectServiceUnitCount(PzServiceUnitCount pzServiceUnitCount);

}
