package com.dhc.rad.modules.pzOrder.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzOrderDao extends CrudDao<PzOrder> {

    /**
     * 批量删除(逻辑删除)
     *
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

    Integer updateNoEatDateToNull(@Param("orderId") String orderId, @Param("userId") String userId);


    Integer reallyDeleteByOrderId(@Param("orderId")String orderId);
}
