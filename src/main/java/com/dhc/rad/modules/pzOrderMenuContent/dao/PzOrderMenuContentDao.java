package com.dhc.rad.modules.pzOrderMenuContent.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzOrderMenuContent.entity.PzOrderMenuContent;

import java.util.List;
@MyBatisDao
public interface PzOrderMenuContentDao extends CrudDao<PzOrderMenuContent> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);
}
