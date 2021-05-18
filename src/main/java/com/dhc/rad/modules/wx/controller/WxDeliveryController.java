package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;
import com.dhc.rad.modules.pzBoxCode.service.PzBoxCodeService;
import com.dhc.rad.modules.pzDelivery.entity.PzDelivery;
import com.dhc.rad.modules.pzDelivery.service.PzDeliveryService;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.entity.OrderVo;
import com.dhc.rad.modules.wx.service.OrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxDelivery")
public class WxDeliveryController {

    @Autowired
    private PzDeliveryService pzDeliveryService;

    @Autowired
    private PzBoxCodeService pzBoxCodeService;

    @Autowired
    private OrderVoService orderVoService;


    /**
     * @Description: 根据箱子编码存储配送信息
     * @Param: boxCode
     * @return: Map<String ,   Object>
     * @Date: 2021/5/7
     */
//    @RequestMapping(value = "saveByBoxCode", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> saveByBoxCode(@RequestParam("boxCode") String boxCode) {
//        Map<String, Object> returnMap = new HashMap<>();
//        //判断boxCode是否为空
//        if (StringUtils.isEmpty(boxCode)) {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.PARMERROR);
//            returnMap.put("message", ConstantUtils.ResCode.ParameterException);
//            return returnMap;
//        }
//        //判断当前用户是否具有配送权限
//        if (!UserUtils.getRoleFlag("delivery")) {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.PASSLIMITS);
//            returnMap.put("message", ConstantUtils.ResCode.PASSLIMITSMSG);
//            return returnMap;
//        }
//        Integer countByBoxCode = pzBoxCodeService.findCountByBoxCode(boxCode);
//        if (countByBoxCode == 0) {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.PARMERROR);
//            returnMap.put("message", ConstantUtils.ResCode.ParameterException);
//            return returnMap;
//        }
//
//        //根据boxCode查询相关信息
//        PzBoxCode pzBoxCode = pzBoxCodeService.findByBoxCode(boxCode);
//        String restaurantId = pzBoxCode.getRestaurantId();
//        String serviceUnitId = pzBoxCode.getServiceUnitId();
//        String boxId = pzBoxCode.getId();
//        //判断该用户是否在对应的餐厅下
//        Office office = UserUtils.getUser().getOffice();
//        String officeId = office.getId();
//        if (!officeId.equals(restaurantId)) {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.PASSLIMITS);
//            returnMap.put("message", ConstantUtils.ResCode.PASSLIMITSMSG);
//            return returnMap;
//        }
//
//
//        //存储配送信息
//        PzDelivery pzDelivery = new PzDelivery();
//        //餐厅id
//        pzDelivery.setRestaurantId(restaurantId);
//        //服务单元id
//        pzDelivery.setServiceUnitId(serviceUnitId);
//        pzDelivery.setBoxId(boxId);
//
//        //配送日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String nowDate = sdf.format(new Date());
//        pzDelivery.setEatDate(nowDate);
//
//        //存储配送信息
//        Integer flag = pzDeliveryService.saveOrUpdateDelivery(pzDelivery);
//
//        if (flag > 0) {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
//            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
//        } else {
//            returnMap.put("data", null);
//            returnMap.put("status", ConstantUtils.ResCode.SERVERERROR);
//            returnMap.put("message", ConstantUtils.ResCode.UPDATEFAIL);
//        }
//
//        return returnMap;
//    }


    @RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderInfo() {
        Map<String, Object> returnMap = new HashMap<>();
        String officeId = UserUtils.getUser().getOffice().getId();

        //获取当前时间本周吃饭时间集合
        List<String> currentWeekDateList = TimeUtils.getCurrentWeekEatDate();
        //时间用逗号","进行拼接
        String crrentWeek = currentWeekDateList.stream().collect(Collectors.joining(",")) + ",";

        //获取当天时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String eatDate = sdf.format(new Date());

        OrderVo ov = new OrderVo();
        ov.setEatDate(eatDate);
        ov.setEatDates(crrentWeek);
        ov.setOfficeId(officeId);
        List<OrderVo> orderList = orderVoService.findServiceUnitOrder(ov);
        if (orderList.size() > 0) {
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            for (OrderVo orderVo : orderList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userNo", orderVo.getUserNo());
                map.put("userName", orderVo.getUserName());
                map.put("menuName", orderVo.getMenuName());
                map.put("serviceUnit", orderVo.getServiceUnit());
                map.put("restaurantName", orderVo.getRestaurantName());
                map.put("areaLocation", orderVo.getAreaLocation());
                map.put("boxName", orderVo.getBoxName());
                if (StringUtils.isNotBlank(orderVo.getNoEatDate()) && orderVo.getNoEatDate().indexOf(eatDate) > 0) {
                    map.put("eatFlat", false);
                } else {
                    map.put("eatFlat", true);
                }
                map.put("deliveryFlag", orderVo.isDeliveryFlag());
                data.add(map);
            }


            returnMap.put("data", data);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        } else {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }


        return returnMap;
    }


}
