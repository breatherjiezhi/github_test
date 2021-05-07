package com.dhc.rad.modules.pzOrder.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzOrder.dao.PzOrderDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzOrderService extends CrudService<PzOrderDao, PzOrder> {

    @Autowired
    private PzOrderDao pzOrderDao;

    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {
        return  pzOrderDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public Integer saveOrder(String userId, String menuId) {
        PzOrder pzOrder = new PzOrder();
        pzOrder.setUserId(userId);
        pzOrder.setMenuId(menuId);
        pzOrder.preInsert();
        return pzOrderDao.insert(pzOrder);
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzOrder pzOrder) {
        if(ObjectUtils.isNotEmpty(pzOrder)){
            if(StringUtils.isNotBlank(pzOrder.getId())){
                pzOrder.preUpdate();
                return pzOrderDao.update(pzOrder);
            }else{
                pzOrder.preInsert();
                return pzOrderDao.insert(pzOrder);
            }
        }
        return 0;
    }
}
