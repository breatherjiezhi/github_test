package com.dhc.rad.modules.pzCaterersCensus.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzMenuCount;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzServiceUnitCount;

import java.util.List;

@MyBatisDao
public interface PzMenuCountDao extends CrudDao<PzMenuCount> {

     List<PzMenuCount> selectNextWeekCount(PzMenuCount pzMenuCount);

}
