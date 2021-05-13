package com.dhc.rad.modules.pzCaterersCensus.controller;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzMenuCount;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzServiceUnitCount;
import com.dhc.rad.modules.pzCaterersCensus.service.PzMenuCountService;
import com.dhc.rad.modules.pzCaterersCensus.service.PzServiceUnitCountService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "${adminPath}/pzCaterersCensus")
public class pzCaterersCensusController extends BaseController {


    @Autowired
    private PzMenuCountService pzMenuCountService;

    @Autowired
    private PzServiceUnitCountService pzServiceUnitCountService;



    @RequestMapping(value = {"tomorrowOrderlist"})
    public String tomorrowOrderlist(PzServiceUnitCount pzServiceUnitCount, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzCaterersCensus/tomorrowOrderlist";
    }


    /**
     * 统计明日订单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"searchTomorrowOrder"})
    @ResponseBody
    public Map<String,Object> searchTomorrowOrder( HttpServletRequest request, HttpServletResponse response){

        PzServiceUnitCount pzServiceUnitCount = new PzServiceUnitCount();
        //获取餐厅id
        String restaurantId = UserUtils.getUser().getOffice().getId();

        //获取明日日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        String noEatDate = sdf.format(c.getTime());

        //获取当前时间本周吃饭时间集合 用于查询本周订单
        List<String> currentWeekDateList = TimeUtils.getCurrentWeekEatDate();
        //时间用逗号","进行拼接
        String eatDate = currentWeekDateList.stream().collect(Collectors.joining(",")) + ",";

        pzServiceUnitCount.setRestaurantId(restaurantId);
        pzServiceUnitCount.setNoEatDate(noEatDate);
        pzServiceUnitCount.setEatDate(eatDate);

        Page<PzServiceUnitCount> page = pzServiceUnitCountService.selectServiceUnitCount(new Page<>(request, response), pzServiceUnitCount);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }



    @RequestMapping(value = {"nextWeekOrderlist"})
    public String nextWeekOrderlist(PzMenuCount pzMenuCount, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzCaterersCensus/nextWeekOrderlist";
    }


    /**
     * 统计明日订单
     * @param pzMenuCount
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"searchNextWeekOrder"})
    @ResponseBody
    public Map<String,Object> searchNextWeekOrder(HttpServletRequest request, HttpServletResponse response){

        PzMenuCount pzMenuCount = new PzMenuCount();
        //获取餐厅id
        String restaurantId = UserUtils.getUser().getOffice().getId();

        //获取明日日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        String noEatDate = sdf.format(c.getTime());

        //获取当前时间本周吃饭时间集合 用于查询本周订单
        List<String> currentWeekDateList = TimeUtils.getNextWeekEatDate();
        //时间用逗号","进行拼接
        String eatDate = currentWeekDateList.stream().collect(Collectors.joining(",")) + ",";

        pzMenuCount.setRestaurantId(restaurantId);
        pzMenuCount.setNoEatDate(noEatDate);
        pzMenuCount.setEatDate(eatDate);

        Page<PzMenuCount> page = pzMenuCountService.selectNextWeekCount(new Page<>(request, response),pzMenuCount);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }





}
