package com.dhc.rad.modules.wx.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzOrder.dao.PzOrderDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzScoreLog.dao.PzScoreLogDao;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.pzUserScore.dao.PzUserScoreDao;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.User;
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

    @Autowired
    private PzOrderDao pzOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PzScoreLogDao pzScoreLogDao;

    @Autowired
    private PzUserScoreDao pzUserScoreDao;

    @Transactional(readOnly = false)
    public Integer updateMenuCount(String id, int version) {
        PzMenu pzMenu = new PzMenu();
        pzMenu.setId(id);
        pzMenu.setVersion(version);
        return pzMenuDao.updateMenuCount(pzMenu);
    }

    /**
     * @Description: 订餐功能
     * @Param: menuId:套餐编号 remainMenuCount:剩余套餐数量 pzOrder:订单实体 user:用户实体 pzScoreLog:积分记录实体
     * @return: Integer
     * @Date: 2021/4/28
     */
    @Transactional
    public Integer orderMenu(PzMenu pzMenu, PzOrder pzOrder, User user, PzScoreLog pzScoreLog) {


        //更新套餐余量
        Integer updateMenuCount = updateMenuCount(pzMenu.getId(), pzMenu.getVersion());

        if (updateMenuCount == null || updateMenuCount == 0) {
            return 0;
        }

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

    @Transactional
    public Integer saveOrUpdate(PzOrder pzOrder, PzScoreLog pzScoreLog, PzUserScore pzUserScore) {
        //新增/更新个人餐厅积分信息
        Integer updateUserScore = null;
        if (pzUserScore.getId() == null) {
            pzUserScore.preInsert();
            updateUserScore = pzUserScoreDao.insert(pzUserScore);
        } else {
            pzUserScore.preUpdate();
            updateUserScore = pzUserScoreDao.updateUserScore(pzUserScore);
        }
        //修改订单数据
        pzOrder.preUpdate();
        Integer update = pzOrderDao.update(pzOrder);
        //新增记录信息
        pzScoreLog.preInsert();
        Integer insert = pzScoreLogDao.insert(pzScoreLog);

        return (updateUserScore > 0 && update > 0 && insert > 0) ? 1 : 0;
    }

    @Transactional
    public Integer updateData(PzUserScore pzUserScore, PzScoreLog pzScoreLog, PzOrder pzOrder) {
        //更新pzUserScore
        pzUserScore.preUpdate();
        Integer updateUserScore = pzUserScoreDao.updateUserScore(pzUserScore);
        //新增pzScoreLog
        pzScoreLog.preInsert();
        int insertLog = pzScoreLogDao.insert(pzScoreLog);
        //更新pzOrder
        pzOrder.preUpdate();
        int updateOrder = pzOrderDao.update(pzOrder);
        return (updateUserScore > 0 && insertLog > 0 && updateOrder > 0) ? 1 : 0;
    }
}
