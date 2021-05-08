package com.dhc.rad.modules.wx.service;


import com.dhc.rad.modules.wx.dao.OrderVoDao;
import com.dhc.rad.modules.wx.entity.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderVoService {


    @Autowired
    private OrderVoDao orderVoDao;



    public  List<OrderVo> findServiceUnitOrder(OrderVo orderVo){
        return   orderVoDao.findServiceUnitOrder(orderVo);
    }


}
