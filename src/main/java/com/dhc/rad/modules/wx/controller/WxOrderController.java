package com.dhc.rad.modules.wx.controller;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modbus.entity.func.Util;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzOrder.service.PzOrderService;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.service.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxOrder")
public class WxOrderController extends BaseController {

    @Autowired
    private WxOrderService wxOrderService;

    @Autowired
    private PzMenuService pzMenuService;

    @Autowired
    private PzOrderService pzOrderService;

    @Autowired
    private SystemService systemService;


    @RequestMapping(value = "orderMenu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderMenu(@RequestParam("menuId") String menuId) {
        Map<String, Object> returnMap = new HashMap<>();
        User user = UserUtils.getUser();
        String userId = user.getId();

        //获取当前时间下一周时间集合
        List<String> nextWeekDateList = TimeUtils.getNextWeekEatDate();

        //根据当前用户id 根据当前时间获取下一周1-7时间
        String nextDate = nextWeekDateList.stream().collect(Collectors.joining(","));

        PzOrder order = new PzOrder();
        order.setEatDate(nextDate);
        order.setUserId(userId);

        List<PzOrder> pzOrderList = pzOrderService.findList(order);

        if(pzOrderList.size()!=0){
            addMessageAjax(returnMap, "0", "下周套餐已预定,请返回");
            return returnMap;
        }

        //menuId非空判断
        if (StringUtils.isBlank(menuId)) {
            addMessageAjax(returnMap, "0", "套餐编号不能为空");
            return returnMap;
        }
        //userId非空判断
        if (StringUtils.isBlank(userId)) {
            addMessageAjax(returnMap, "0", "用户编号不能为空");
            return returnMap;
        }

        //判断用户是否有充足的餐券数
        //获取用户当前剩余餐券数

        BigDecimal userIntegral = null;
        if (user != null) {
            User serviceUser = systemService.getUserId(userId);
            userIntegral = serviceUser.getUserIntegral();
        }

        //计算餐券数
        BigDecimal couponCount = BigDecimal.valueOf(nextWeekDateList.size());
        //剩余积分
        BigDecimal remainIntegral = userIntegral.subtract(couponCount);
        //如果不足，返回 重新充值
        int compareTo = remainIntegral.compareTo(BigDecimal.ZERO);
        if (compareTo == 0 || compareTo < 0) {
            addMessageAjax(returnMap, "0", "餐券已使用完毕，请充值");
            return returnMap;
        }
        //修改套餐余量
        //查询套餐余量
        Integer menuCount = pzMenuService.findMenuCount(menuId);
        //剩余套餐数量
        BigDecimal remainMenuDecimal = BigDecimal.valueOf(menuCount).subtract(couponCount);
        Integer compare = remainMenuDecimal.compareTo(BigDecimal.ZERO);
        if (compare < 0) {
            addMessageAjax(returnMap, "0", "套餐余量不足，请选择其他套餐");
            return returnMap;
        }
        Integer remainMenuCount = Integer.parseInt(String.valueOf(remainMenuDecimal));

        //生成订单
        PzOrder pzOrder = new PzOrder();
        //套餐id
        pzOrder.setMenuId(menuId);
        //用户id
        pzOrder.setUserId(userId);
        //套餐积分
        pzOrder.setMenuIntegral(couponCount);
        //吃饭日期
        pzOrder.setEatDate(nextDate);
        //餐厅id
        PzMenu pzMenu = pzMenuService.get(menuId);
        if (pzMenu == null) {
            addMessageAjax(returnMap, "0", "查询不到套餐信息，请重新核对");
            return returnMap;
        }
        pzOrder.setRestaurantId(pzMenu.getRestaurantId());
        //服务单元id
        String serviceUnitId = UserUtils.getUser().getOffice().getId();
        pzOrder.setServiceUnitId(serviceUnitId);

        //扣除积分
        user.setUserIntegral(remainIntegral);

        //新增记录
        PzScoreLog pzScoreLog = new PzScoreLog();
        //用户id
        pzScoreLog.setUserId(user.getId());
        //积分类型
        pzScoreLog.setScoreType(Global.SCORE_TYPE_DEDUCT);
        //积分分类
        pzScoreLog.setScoreClassify(Global.SCORE_CLASSIFY_COUPON);
        //积分变动
        pzScoreLog.setScoreChange("-" + pzOrder.getMenuIntegral());
        //变动积分描述
        String description = user.getId() +"-"+ user.getLoginName() + "：" + pzScoreLog.getScoreChange() + "积分";
        pzScoreLog.setScoreDescription(description);


        //订餐
        Integer flag = wxOrderService.orderMenu(menuId, remainMenuCount, pzOrder, user, pzScoreLog);

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "订餐成功,剩余：" + remainMenuCount);
        } else {
            addMessageAjax(returnMap, "0", "订餐失败");
        }

        return returnMap;
    }

    /**
    * @Description:  查询当前用户下一周的订餐信息
    * @Param:  null
    * @return:  Map<String,Object>
    * @Author: zhengXiang
    * @Date: 2021/4/29
    */
    @RequestMapping(value = "findOrderNextWeek", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findOrderNextWeekById(){
        Map<String,Object> returnMap = new HashMap<>();
        //获取nextWeekEatDate
        List<String> nextWeekEatDateList = TimeUtils.getNextWeekEatDate();
        String nextWeekEatDate = nextWeekEatDateList.stream().collect(Collectors.joining(","));

        String userId = UserUtils.getUser().getId();
        PzOrder order = new PzOrder();
        order.setUserId(userId);
        order.setEatDate(nextWeekEatDate);
        List<PzOrder> list = pzOrderService.findList(order);
        if(list.size()>0){
            Map<String,Object> temp = new HashMap<>();
            PzOrder  pzOrder = list.get(0);
            temp.put("orderId", pzOrder.getId());
            temp.put("eatDate", pzOrder.getEatDate());
            temp.put("noEatDate", pzOrder.getEatDate());
            PzMenu pzMenu = pzMenuService.get(pzOrder.getMenuId());
            temp.put("menuId", pzMenu.getId());
            temp.put("name",pzMenu.getMenuName());
            temp.put("limited", pzMenu.getMenuLimited());
            temp.put("num", pzMenu.getMenuCount());
            temp.put("menuDescript",pzMenu.getMenuDescription() );
            temp.put("imgUrl", Util.getImgUrl() +pzMenu.getMenuImgUrl());
            temp.put("menuType",  pzMenu.getMenuType());
            temp.put("price",  TimeUtils.getNextWeekEatDate().size());
            returnMap.put("data", list.get(0));
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        }else{
            returnMap.put("data", new ArrayList<PzOrder>());
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }

        return returnMap;
    }


    /**
     * @Description:  查询当前用户当前周的订餐信息
     * @Param:  null
     * @return:  Map<String,Object>
     * @Author: zhengXiang
     * @Date: 2021/4/29
     */
    @RequestMapping(value = "findOrderCurrentWeek", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findOrderCurrentWeek(){
        Map<String,Object> returnMap = new HashMap<>();
        //获取currentWeekEatDate
        List<String> currentWeekEatDateList = TimeUtils.getCurrentWeekEatDate();
        String currentWeekEatDate = currentWeekEatDateList.stream().collect(Collectors.joining(","));

        String userId = UserUtils.getUser().getId();
        PzOrder order = new PzOrder();
        order.setUserId(userId);
        order.setEatDate(currentWeekEatDate);
        List<PzOrder> list = pzOrderService.findList(order);
        if(list.size()>0){
            Map<String,Object> temp = new HashMap<>();
            PzOrder  pzOrder = list.get(0);
            temp.put("orderId", pzOrder.getId());
            temp.put("eatDate", pzOrder.getEatDate());
            temp.put("noEatDate", pzOrder.getEatDate());
            PzMenu pzMenu = pzMenuService.get(pzOrder.getMenuId());
            temp.put("menuId", pzMenu.getId());
            temp.put("name",pzMenu.getMenuName());
            temp.put("limited", pzMenu.getMenuLimited());
            temp.put("num", pzMenu.getMenuCount());
            temp.put("menuDescript",pzMenu.getMenuDescription() );
            temp.put("imgUrl", Util.getImgUrl() +pzMenu.getMenuImgUrl());
            temp.put("menuType",  pzMenu.getMenuType());
            temp.put("price",  TimeUtils.getNextWeekEatDate().size());
            returnMap.put("data", list.get(0));
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        }else{
            returnMap.put("data", new ArrayList<PzOrder>());
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }
        return returnMap;
    }


}
