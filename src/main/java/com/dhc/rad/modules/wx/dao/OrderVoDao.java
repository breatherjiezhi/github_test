package com.dhc.rad.modules.wx.dao;

import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.wx.entity.OrderVo;

import java.util.List;

@MyBatisDao
public interface OrderVoDao {
    List<OrderVo> findServiceUnitOrder(OrderVo orderVo);
}
