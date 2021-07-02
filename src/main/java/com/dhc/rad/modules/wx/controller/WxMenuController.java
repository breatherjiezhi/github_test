package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.modbus.entity.func.Util;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.utils.ConfigInfoUtils;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.utils.RedissonUtils;
import jodd.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "${adminPath}/wx/wxMenu")
public class WxMenuController {


    @Autowired
    private PzMenuService pzMenuService;

    @Autowired
    private OfficeService officeService;


    private static final RedissonClient redissonClient = RedissonUtils.getRedissonClient(1);


    @RequestMapping(value = {"findMenuByRid"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findMenuByRid(String rid, HttpServletRequest request, HttpServletResponse response, Model model) {

        //设置锁定资源名称
        RLock lock = redissonClient.getLock("menuByRidLock");
        try {
            lock.lock();

            Map<String, Object> returnMap = new HashMap<>();

            if (StringUtils.isBlank(rid)) {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.NODATA);
                returnMap.put("message", ConstantUtils.ResCode.ParameterException);
                return returnMap;
            }

            //获取当前时间本周吃饭时间集合 用于查询本周订单
            List<String> nextWeekEatDateList = TimeUtils.getNextWeekEatDate();
            //时间用逗号","进行拼接
            String eatDate = nextWeekEatDateList.stream().collect(Collectors.joining(",")) + ",";

            List<PzMenu> menuList = pzMenuService.findListByRid(rid, eatDate);

            List<Map<String, Object>> dataList = new ArrayList<>();

            //菜单接口需求id;
            int id = 1;
            for (String eDate : nextWeekEatDateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", "id" + id);
                map.put("eadDate", eDate);
                map.put("eadWeek", TimeUtils.getWeekDay(eDate));
                List<Map<String, Object>> listMap = new ArrayList<>();
                for (PzMenu pzMenu : menuList) {
                    List<PzMenuContent> list = pzMenu.getPzMenuContentList();
                    Map<String, Object> menuContentMap = new HashMap<>();
                    for (PzMenuContent pzMenuContent : list) {
                        if (eDate.equals(pzMenuContent.getEatDate())) {
                            menuContentMap.put("menuContentId", pzMenuContent.getId());
                            menuContentMap.put("menuName", pzMenu.getMenuName() + "套餐");
                            menuContentMap.put("menuDetail", pzMenuContent.getMenuDetail());
                            menuContentMap.put("menuImg", Util.getImgUrl() + pzMenu.getMenuImgUrl());
                            menuContentMap.put("num", 0);  //后续限量改为显示限量数值
                            listMap.add(menuContentMap);
                        }
                    }
                }
                map.put("category", listMap);
                dataList.add(map);
                id++;
            }

            returnMap.put("data", dataList);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
            return returnMap;

        } finally {
            // 是否还是锁定状态
            if (lock.isLocked()) {
                // 是否是当前执行线程的锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock(); // 释放锁
                }
            }
        }
    }


    @RequestMapping(value = {"findRestaurant"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findRestaurant(HttpServletRequest request, HttpServletResponse response, Model model) {

        //设置锁定资源名称
        RLock lock = redissonClient.getLock("restaurantLock");
        try {
            lock.lock();

            List<Office> officeList = officeService.findRestaurantOffice();

            //获取当前时间下周吃饭时间集合
            List<String> nextWeekEatDateList = TimeUtils.getNextWeekEatDate();
            //时间用逗号","进行拼接
            String eatDate = nextWeekEatDateList.stream().collect(Collectors.joining(",")) + ",";


            List<Map<String, Object>> dataList = new ArrayList<>();

            for (Office office : officeList) {
                Map<String, Object> map = new HashMap<>();
                map.put("restaurantId", office.getId());
                map.put("restaurantName", office.getName());
                List<PzMenu> menuList = pzMenuService.findListByRid(office.getId(), eatDate);
                StringBuffer temp = new StringBuffer();
                for (PzMenu pzMenu : menuList) {
                    if (StringUtils.isNotBlank(temp)) {
                        temp.append("  ").append(pzMenu.getMenuName() + "套餐:" + pzMenu.getPzMenuContentString());
                    } else {
                        temp.append(pzMenu.getMenuName() + "套餐:" + pzMenu.getPzMenuContentString());
                    }

                }
                map.put("menuDetail", temp);
                map.put("imgUrl", Util.getImgUrl() + ConfigInfoUtils.getConfigVal(office.getName() + "Image"));
                map.put("restaurantColor", ConfigInfoUtils.getConfigVal(office.getName() + "Color"));
                dataList.add(map);
            }

            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("data", dataList);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
            return returnMap;
        } finally {
            // 是否还是锁定状态
            if (lock.isLocked()) {
                // 是否是当前执行线程的锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock(); // 释放锁
                }
            }
        }
    }

}
