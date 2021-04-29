package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.quartz.entity.GlobalConstant;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.service.OfficeService;
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
        List<PzMenu> menuList = pzMenuService.findListByRid(rid);
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
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("data", officeList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }

    @RequestMapping(value = {"findAllRestaurant"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  findAllRestaurant(HttpServletRequest request, HttpServletResponse response, Model model) {
        String localAddr = request.getLocalAddr();
        int serverPort = request.getServerPort();
        String url= "https://" + localAddr + ":" + serverPort + File.separator;
        List<Office> officeList = officeService.findRestaurantOffice();
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        for (Office office : officeList) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("id", office.getId());
            temp.put("name", office.getName());
            List<PzMenu> menuList = pzMenuService.findListByRid(office.getId());
            List<Map<String,Object>> category = new ArrayList<Map<String,Object>>();
            for (PzMenu pzMenu : menuList) {
                Map<String,Object> temp1 = new HashMap<>();
                temp1.put("id", pzMenu.getId());
                temp1.put("name",pzMenu.getMenuName());
                temp1.put("limited", pzMenu.getMenuLimited());
                temp1.put("num", pzMenu.getMenuCount());
                temp1.put("menuDescript",pzMenu.getMenuDescription() );
                temp1.put("imgUrl", url+pzMenu.getMenuImgUrl());
                temp1.put("menuType",  pzMenu.getMenuType());

                //获取当前时间下一周时间集合
                List<String> dateList = TimeUtils.getNextWeekDateList();
                //去除下一周节假日日期
                List<String> eatDateList = new ArrayList<>();
                eatDateList = dateList.stream()
                        .filter(s -> {
                            Holiday holiday = holidayService.getByDate(s);
                            return holiday == null;
                        })
                        .collect(Collectors.toList());
                temp1.put("price",  eatDateList.size());
                category.add(temp1);
            }
            temp.put("category", category);
            dataList.add(temp);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("data", dataList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }

}
