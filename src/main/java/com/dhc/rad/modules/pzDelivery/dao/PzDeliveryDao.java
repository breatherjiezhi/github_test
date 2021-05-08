package com.dhc.rad.modules.pzDelivery.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzDelivery.entity.PzDelivery;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzDeliveryDao extends CrudDao<PzDelivery> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);


    Integer updateDelivery(PzDelivery pzDelivery);


    PzDelivery getDelivery(PzDelivery pzDelivery);
}
