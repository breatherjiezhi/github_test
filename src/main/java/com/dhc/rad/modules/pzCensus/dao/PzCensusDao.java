package com.dhc.rad.modules.pzCensus.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzCensus.entity.PzCensus;

import java.util.List;

@MyBatisDao
public interface PzCensusDao extends CrudDao<PzCensus> {

    List<PzCensus> findCensusList(PzCensus pzCensus);


}
