package com.dhc.rad.modules.wx.controller;

import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzOrder.service.PzOrderService;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.service.WxOrderService;
import com.dhc.rad.modules.wx.utils.WeekUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private PzOrderService pzOrderService;

    @Autowired
    private WxOrderService wxOrderService;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private PzMenuService pzMenuService;


    @RequestMapping(value = "getDate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getDate() {
        Map<String, Object> returnMap = new HashMap<>();
        List list = WeekUtils.getNextWeekDateList();
        returnMap.put("list", list);
        return returnMap;
    }

    @RequestMapping(value = "orderMenu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderMenu(@RequestParam("menuId") String menuId, @RequestParam("userId") String userId) {
        Map<String, Object> returnMap = new HashMap<>();

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
        User user = UserUtils.getUser();
        BigDecimal userIntegral = null;
        if (user != null) {
            userIntegral = user.getUserIntegral();
        }
        //获取当前时间下一周时间集合
        List<String> dateList = WeekUtils.getNextWeekDateList();
        //去除下一周节假日日期
        List<String> eatDateList = new ArrayList<>();
        eatDateList = dateList.stream().filter(s -> {
            Holiday holiday = holidayService.getByDate(s);
            return holiday == null ? true : false;
        }).collect(Collectors.toList());

        //计算餐券数
        BigDecimal couponCount = BigDecimal.valueOf(eatDateList.size());
        //如果不足，返回 重新充值
        BigDecimal remainIntegral = userIntegral.subtract(couponCount);
        int compareTo = remainIntegral.compareTo(BigDecimal.ZERO);
        if (compareTo == 0 || compareTo < 0) {
            addMessageAjax(returnMap, "0", "餐券已使用完毕，请充值");
            return returnMap;
        }
        //修改套餐余量
        Integer menuCount = pzMenuService.findMenuCount(menuId);
        //剩余套餐数量
        Integer remainMenuCount = Integer.parseInt(String.valueOf(BigDecimal.valueOf(menuCount).subtract(couponCount)));
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
        PzMenu pzMenu = pzMenuService.get(menuId);
        if(pzMenu==null){
            addMessageAjax(returnMap, "0", "查询不到套餐信息，请重新核对");
            return returnMap;
        }
        pzOrder.setRestaurantId(pzMenu.getRestaurantId());
        //根据用户id查询服务单元信息和餐厅信息
        String serviceUnitId = UserUtils.getUser().getOffice().getId();
        pzOrder.setServiceUnitId(serviceUnitId);

        returnMap = wxOrderService.orderMenu(menuId, userId, remainMenuCount);

        return returnMap;
    }


}
