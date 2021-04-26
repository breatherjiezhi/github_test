package com.dhc.rad.modules.wxOrder.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class WxOrderService extends CrudService<PzMenuDao, PzMenu> {

    @Autowired
    private PzMenuDao pzMenuDao;

    @Transactional(readOnly = false)
    public Integer findMenuCount(String id) {
        return pzMenuDao.findMenuCount(id);
    }

    @Transactional(readOnly = false)
    public Integer updateMenuCount(String id, int realStock) {
        PzMenu pzMenu = new PzMenu();
        pzMenu.setId(id);
        pzMenu.setMenuCount(realStock);
        return pzMenuDao.updateMenuCount(pzMenu);
    }
}
