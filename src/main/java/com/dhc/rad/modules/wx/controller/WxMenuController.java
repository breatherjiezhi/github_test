package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.modbus.entity.func.Util;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "${adminPath}/wx/wxMenu")
public class WxMenuController {


    @Autowired
    private PzMenuService pzMenuService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private HolidayService holidayService;



    @RequestMapping(value = {"findMenuByRid"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  findMenuByRid(String rid, HttpServletRequest request, HttpServletResponse response, Model model) {

        //获取当前时间本周吃饭时间集合 用于查询本周订单
        List<String> currentWeekDateList = TimeUtils.getCurrentWeekEatDate();
        //时间用逗号","进行拼接
        String eatDate = currentWeekDateList.stream().collect(Collectors.joining(",")) + ",";
        List<PzMenu> menuList = pzMenuService.findListByRid(rid,eatDate);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("data", menuList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }


    @RequestMapping(value = {"findRestaurant"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  findRestaurant(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Office> officeList = officeService.findRestaurantOffice();

        //获取当前时间下周吃饭时间集合
        List<String> currentWeekDateList = TimeUtils.getNextWeekEatDate();
        //时间用逗号","进行拼接
        String eatDate = currentWeekDateList.stream().collect(Collectors.joining(",")) + ",";


        List<Map<String,Object>> dataList = new ArrayList<>();

        for (Office office : officeList) {
            Map<String,Object> map = new HashMap<>();
            map.put("restaurantId",office.getId());
            map.put("restaurantName",office.getName());
            List<PzMenu> menuList = pzMenuService.findListByRid(office.getId(),eatDate);
            StringBuffer temp = new StringBuffer();
            for (PzMenu pzMenu : menuList) {
                if(StringUtils.isNotBlank(temp)){
                    temp.append("\n\n").append(pzMenu.getMenuName()+"套餐:"+pzMenu.getPzMenuContentString());
                }else{
                    temp.append(pzMenu.getMenuName()+"套餐:"+pzMenu.getPzMenuContentString());
                }

            }
            map.put("menuDetail",temp);
            dataList.add(map);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("data", dataList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }


}
