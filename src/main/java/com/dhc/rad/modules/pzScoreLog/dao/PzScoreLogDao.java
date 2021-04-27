package com.dhc.rad.modules.pzScoreLog.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzScoreLogDao extends CrudDao<PzScoreLog> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

    List<PzScoreLog> findScoreById(PzScoreLog pzScoreLog);
}
