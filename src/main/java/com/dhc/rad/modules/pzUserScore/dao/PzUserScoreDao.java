package com.dhc.rad.modules.pzUserScore.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzUserScoreDao extends CrudDao<PzUserScore> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

}
