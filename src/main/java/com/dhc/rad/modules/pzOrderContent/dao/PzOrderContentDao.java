package com.dhc.rad.modules.pzOrderContent.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzOrderContent.entity.PzOrderContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PzOrderContentDao extends CrudDao<PzOrderContent> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

    PzOrderContent getByContentIdAndCreateBy(@Param("contentId") String contentId, @Param("userId") String userId);



    List<Map<String, Object>> findListByOrderId(@Param("orderId") String orderId);

    Integer findCountByOrderId(@Param("orderId") String orderId, @Param("userId") String userId,@Param("eatFlag")String eatFlag);

    Integer deleteContentByOrderId(@Param("orderId")String orderId, @Param("userId") String userId);

    Integer reallyDeleteContentIds(@Param("orderId")String orderId, @Param("userId") String userId);
}
