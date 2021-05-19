package com.dhc.rad.modules.pzOrderContent.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzOrderContent.entity.PzOrderContent;

import java.util.List;
@MyBatisDao
public interface PzOrderContentDao extends CrudDao<PzOrderContent> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);
}
