package com.dhc.rad.modules.wx.dao;

import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.wx.entity.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface OrderVoDao {
    List<OrderVo> findServiceUnitOrder(OrderVo orderVo);

    List<Map<String,Object>> findMenuRanking(@Param("eatDates") String eatDates,@Param("pageSize") Integer pageSize);

}
