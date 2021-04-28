package com.dhc.rad.modules.wx.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.holiday.dao.HolidayDao;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzOrder.dao.PzOrderDao;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.utils.WeekUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class WxOrderService extends CrudService<PzMenuDao, PzMenu> {

    @Autowired
    private PzMenuDao pzMenuDao;

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private PzOrderDao pzOrderDao;

    @Autowired
    private UserDao userDao;

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


    @Transactional
    public Map<String, Object> orderMenu(String menuId, String userId, Integer remainMenuCount) {
        Map<String, Object> returnMap = new HashMap<>();
        //判断用户是否有充足的餐券数
        //获取用户当前剩余餐券数
        User user = UserUtils.getUser();
        BigDecimal userIntegral = null;
        if(user!=null){
             userIntegral = user.getUserIntegral();
        }
        //获取当前时间下一周时间集合
        List<String> dateList = WeekUtils.getNextWeekDateList();
        //去除下一周节假日日期
        List<String> eatDateList = new ArrayList<>();
       eatDateList =  dateList.stream().filter(s -> {
            Holiday holiday = holidayDao.getByDate(s);
            return holiday==null ? true : false;
        }).collect(Collectors.toList());
        //计算餐券数
        BigDecimal couponCount = BigDecimal.valueOf(eatDateList.size());
        //如果不足，返回 重新充值
        BigDecimal remainIntegral = userIntegral.subtract(couponCount);
        int compareTo = remainIntegral.compareTo(BigDecimal.ZERO);
        if(compareTo==0 || compareTo < 0){
            returnMap.put("0", "餐券已使用完毕，请充值");
            return returnMap;
        }
        //修改套餐余量
        Integer menuCount = pzMenuDao.findMenuCount(menuId);
        menuCount = Integer.parseInt(String.valueOf(BigDecimal.valueOf(menuCount).subtract(couponCount)));
        Integer updateMenuCount = updateMenuCount(menuId, menuCount);
        if(updateMenuCount<0){
            returnMap.put("0", "修改套餐失败，请重试");
            return returnMap;
        }
        //生成订单
        PzOrder pzOrder = new PzOrder();
        //套餐id
        pzOrder.setMenuId(menuId);
        //用户id
        pzOrder.setUserId(userId);
        //套餐积分
        pzOrder.setMenuIntegral(couponCount);
        //吃饭日期
        String eatDate = eatDateList.stream().collect(Collectors.joining(","));
        pzOrder.setEatDate(eatDate);
        //获取餐厅id和服务单元id
        PzMenu pzMenu = pzMenuDao.get(menuId);
        if(pzMenu==null){
            returnMap.put("0", "查询不到套餐信息，请重新核对");
            return returnMap;
        }
        pzOrder.setRestaurantId(pzMenu.getRestaurantId());
        //根据用户id查询服务单元信息和餐厅信息
        String serviceUnitId = UserUtils.getUser().getOffice().getId();
        pzOrder.setServiceUnitId(serviceUnitId);
        pzOrder.preInsert();
        //新增订单
        Integer insertOrder = pzOrderDao.insert(pzOrder);
        if(insertOrder < 0){
            returnMap.put("0", "新增订单失败，请重试");
            return returnMap;
        }
        //扣除积分
        user.setUserIntegral(remainIntegral);
        user.preUpdate();
        Integer updateIntegral = userDao.update(user);
        if(updateIntegral < 0){
            returnMap.put("0", "扣除积分失败，请重试");
            return returnMap;
        }
        returnMap.put("1", "订餐成功");
        return returnMap;
    }
}
