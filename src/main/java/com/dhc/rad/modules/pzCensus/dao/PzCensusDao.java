package com.dhc.rad.modules.pzCensus.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzCensus.entity.PzCensus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PzCensusDao extends CrudDao<PzCensus> {

    List<PzCensus> findCensusList(PzCensus pzCensus);

    List<PzCensus> findCensusSum(PzCensus pzCensus);

    List<Map<String, Object>> selectUserCensus(@Param("restaurantId") String restaurantId, @Param("officeId") String officeId,
                                               @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                               @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);



    List<String> findEatDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);


}
