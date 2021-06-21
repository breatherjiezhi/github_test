package com.dhc.rad.modules.wx.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenuContent.dao.PzMenuContentDao;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import com.dhc.rad.modules.pzOrder.dao.PzOrderDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzOrderContent.dao.PzOrderContentDao;
import com.dhc.rad.modules.pzOrderContent.entity.PzOrderContent;
import com.dhc.rad.modules.pzScoreLog.dao.PzScoreLogDao;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.pzUserScore.dao.PzUserScoreDao;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PzOrderContentDao pzOrderContentDao;

    @Autowired
    private PzMenuContentDao pzMenuContentDao;



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
    public Integer orderMenu(PzMenu pzMenu, PzOrder pzOrder, List<String> contentIds, User currentUser, PzScoreLog pzScoreLog) {


        //新增订单
        pzOrder.preInsert();

        //订单创建人始终是用户自己,更新人可以是管理员(管理员批量点餐)
        pzOrder.setCreateBy(currentUser);

        Integer insertOrder = pzOrderDao.insert(pzOrder);

        //扣除积分
        currentUser.preUpdate();
        Integer updateIntegral = userDao.updateIntegral(currentUser);

        //新增记录
        pzScoreLog.preInsert();
        int logInsert = pzScoreLogDao.insert(pzScoreLog);

        //更新套餐余量和生成订单详情
        Integer result = 0;
        Integer updateMenuContentResult = 0;
        for (String contentId : contentIds) {
            Integer updateMenuContentCount = null;
            PzOrderContent pzOrderContent = new PzOrderContent();
            pzOrderContent.setContentId(contentId);
            pzOrderContent.setOrderId(pzOrder.getId());
            //套餐限量，需要更新套餐余量
            PzMenuContent pzMenuContent = pzMenuContentDao.get(contentId);
            if("1".equals(pzMenuContent.getMenuLimited())){
                //更新套餐余量
                updateMenuContentCount = pzMenuContentDao.updateMenuContentCount(contentId, pzMenuContent.getVersion());

                if (updateMenuContentCount == null || updateMenuContentCount == 0) {
                    return 0;
                }
            }
            updateMenuContentResult++;
            //新增订单详情
            pzOrderContent.preInsert();
            int insert = pzOrderContentDao.insert(pzOrderContent);
            if(insert > 0){
                result ++;
            }
        }


        //返回
        return (result.equals(contentIds.size()) && updateMenuContentResult.equals(contentIds.size()) && insertOrder > 0 && updateIntegral > 0 && logInsert > 0) ? 1 : 0;
    }

    @Transactional
    public Integer saveOrUpdate(PzOrder pzOrder, PzScoreLog pzScoreLog, PzUserScore pzUserScore, PzOrderContent pzOrderContent) {
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

        //修改pz_order_content信息
        pzOrderContent.preUpdate();
        Integer updateOrderContent = pzOrderContentDao.update(pzOrderContent);

        return (updateUserScore > 0 && update > 0 && insert > 0 && updateOrderContent > 0) ? 1 : 0;
    }

    @Transactional
    public Integer updateData(PzUserScore pzUserScore, PzScoreLog pzScoreLog, PzOrder pzOrder, PzOrderContent pzOrderContent) {
        //更新pzUserScore
        pzUserScore.preUpdate();
        Integer updateUserScore = pzUserScoreDao.updateUserScore(pzUserScore);
        //新增pzScoreLog
        pzScoreLog.preInsert();
        int insertLog = pzScoreLogDao.insert(pzScoreLog);
        //更新pzOrder
        pzOrder.preUpdate();
        int updateOrder = pzOrderDao.update(pzOrder);
        //更新pzOrderContent
        pzOrderContent.preUpdate();
        int updateOrderContent = pzOrderContentDao.update(pzOrderContent);
        return (updateUserScore > 0 && insertLog > 0 && updateOrder > 0 && updateOrderContent >0) ? 1 : 0;
    }

    public List<Map<String, Object>> findListByOrderId(String orderId) {
        return pzOrderContentDao.findListByOrderId(orderId);
    }


    @Transactional
    public Integer updateInfo(String orderId,String restaurantId, String userId, BigDecimal integral, BigDecimal consumeIntegral) {
        //根据orderId直接删除contentId，并且清空pz_order中no_eat_date字段，sys_user中user_integraL字段更新 pz_user_score中CanteenIntegral
        Integer deleteCount = pzOrderContentDao.reallyDeleteContentIds(orderId,userId);

        /*Integer updateNoEatDateToNull = null;  && updateNoEatDateToNull > 0
        if(order.getNoEatDate()!=null && !"".equals(order.getNoEatDate())){
            updateNoEatDateToNull =pzOrderDao.updateNoEatDateToNull(order.getId(),userId);
        }*/

        User user = new User();
        user.setId(userId);
        user.setUserIntegral(integral);
        Integer updateIntegral = userDao.updateUserIntegral(user);

        Integer updateCanteenIntegral = null;
        if(consumeIntegral.compareTo(BigDecimal.ZERO)==0){
            updateCanteenIntegral = 1;
        }else{
            PzUserScore pzUserScore = new PzUserScore();
            pzUserScore.setCanteenIntegral(consumeIntegral);
            pzUserScore.setUserId(userId);
            pzUserScore.setRestaurantId(restaurantId);
            updateCanteenIntegral = pzUserScoreDao.updateCanteenIntegral(pzUserScore);
        }


        Integer deleteByOrderId = pzOrderDao.reallyDeleteByOrderId(orderId);

        return (deleteCount > 0  && updateIntegral > 0 && updateCanteenIntegral > 0 && deleteByOrderId  > 0) ? 1 : 0;
    }
}
