package com.dhc.rad.modules.wx.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.JedisUtils;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzOrder.dao.PzOrderDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzScoreLog.dao.PzScoreLogDao;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class WxOrderService extends CrudService<PzMenuDao, PzMenu> {

    @Autowired
    private PzMenuDao pzMenuDao;

    @Autowired
    private PzOrderDao pzOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PzScoreLogDao pzScoreLogDao;

    @Transactional(readOnly = false)
    public Integer updateMenuCount(String id, int realStock) {
        PzMenu pzMenu = new PzMenu();
        pzMenu.setId(id);
        pzMenu.setMenuCount(realStock);
        return pzMenuDao.updateMenuCount(pzMenu);
    }

    /**
     * @Description: 订餐功能
     * @Param: menuId:套餐编号 remainMenuCount:剩余套餐数量 pzOrder:订单实体 user:用户实体 pzScoreLog:积分记录实体
     * @return: Integer
     * @Author: zhengXiang
     * @Date: 2021/4/28
     */
    @Transactional
    public Integer orderMenu(String menuId, Integer remainMenuCount, PzOrder pzOrder, User user, PzScoreLog pzScoreLog){


        //更新套餐余量
        Integer updateMenuCount = updateMenuCount(menuId, remainMenuCount);

        //新增订单
        pzOrder.preInsert();
        Integer insertOrder = pzOrderDao.insert(pzOrder);

        //扣除积分
        user.preUpdate();
        Integer updateIntegral = userDao.update(user);

        //新增记录
        pzScoreLog.preInsert();
        int logInsert = pzScoreLogDao.insert(pzScoreLog);

        //返回
        return (updateMenuCount > 0 && insertOrder > 0 && updateIntegral > 0 && logInsert > 0) ? 1 : 0;
    }

}
