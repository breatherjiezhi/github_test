package com.dhc.rad.modules.pzCensus.web;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzCensus.entity.PzCensus;
import com.dhc.rad.modules.pzCensus.service.PzCensusService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/pzCensus")
public class PzCensusController extends BaseController {

    @Autowired
    private PzCensusService pzCensusService;


    @RequestMapping(value = {"list"})
    public String list(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String eatDate = sdf.format(new Date());
        pzCensus.setEatDate(eatDate);
        return "modules/pzCensus/pzServiceUnitCensus";
    }


    /**
     * 按服务单元查询统计,每日订单 按服务单元查看
     *
     * @param pzCensus
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<>();

        Boolean gcsFlag = UserUtils.getRoleFlag("gcs");

        if (!(gcsFlag || UserUtils.getRoleFlag("admin") || UserUtils.getRoleFlag("admins"))) {
            addMessageAjax(returnMap, "0", "当前用户无权限！！！");
            return returnMap;
        }
        String officeId = UserUtils.getUser().getOffice().getId();
        if (gcsFlag) {
            pzCensus.setRestaurantId(officeId);
        }


        if (StringUtils.isBlank(pzCensus.getEatDate())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String eatDate = sdf.format(new Date());
            pzCensus.setEatDate(eatDate);
        }

        Page<PzCensus> page = pzCensusService.searchPage(new Page<>(request, response), pzCensus);

        Page<PzCensus> page1 = pzCensusService.findCensusPage(new Page<>(), pzCensus);
        if (page1.getList().size() > 0) {
            returnMap.put("totalA", page1.getList().get(0).getCountA());
            returnMap.put("totalB", page1.getList().get(0).getCountB());
            returnMap.put("totalC", page1.getList().get(0).getCountC());
        } else {
            returnMap.put("totalA", 0);
            returnMap.put("totalB", 0);
            returnMap.put("totalC", 0);
        }
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }


    @RequestMapping(value = {"menuTotal"})
    @ResponseBody
    public Map<String, Object> menuTotal(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> returnMap = new HashMap<>();
        Boolean gcsFlag = UserUtils.getRoleFlag("gcs");
        if (!(gcsFlag || UserUtils.getRoleFlag("admin") || UserUtils.getRoleFlag("admins"))) {
            addMessageAjax(returnMap, "0", "当前用户无权限！！！");
            return returnMap;
        }
        String officeId = UserUtils.getUser().getOffice().getId();
        if (gcsFlag) {
            pzCensus.setRestaurantId(officeId);
        }
        if (StringUtils.isBlank(pzCensus.getEatDate())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String eatDate = sdf.format(new Date());
            pzCensus.setEatDate(eatDate);
        }
        Page<PzCensus> page = pzCensusService.findCensusPage(new Page<>(), pzCensus);
        if (page.getList().size() > 0) {
            returnMap.put("totalA", page.getList().get(0).getCountA());
            returnMap.put("totalB", page.getList().get(0).getCountB());
            returnMap.put("totalC", page.getList().get(0).getCountC());
            returnMap.put("totalD", page.getList().get(0).getCountD());
            returnMap.put("totalE", page.getList().get(0).getCountE());
            returnMap.put("totalF", page.getList().get(0).getCountF());
        } else {
            returnMap.put("totalA", 0);
            returnMap.put("totalB", 0);
            returnMap.put("totalC", 0);
            returnMap.put("totalD", 0);
            returnMap.put("totalE", 0);
            returnMap.put("totalF", 0);
        }
        return returnMap;
    }


    @RequestMapping(value = {"censusList"})
    public String censusList(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<String> timeList = TimeUtils.getNextWeekDateList();
        pzCensus.setBeginDate(timeList.get(0));
        pzCensus.setEndDate(timeList.get(timeList.size() - 1));
        return "modules/pzCensus/pzCensus";
    }


    /**
     * 按每天统计套餐数量
     *
     * @param pzCensus
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"findCensus"})
    @ResponseBody
    public Map<String, Object> findCensus(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(pzCensus.getBeginDate()) && StringUtils.isBlank(pzCensus.getEndDate())) {
            List<String> timeList = TimeUtils.getNextWeekDateList();
            pzCensus.setBeginDate(timeList.get(0));
            pzCensus.setEndDate(timeList.get(timeList.size() - 1));
        }


        Map<String, Object> returnMap = new HashMap<>();
        Boolean gcsFlag = UserUtils.getRoleFlag("gcs");
        if (!(gcsFlag || UserUtils.getRoleFlag("admin") || UserUtils.getRoleFlag("admins"))) {
            addMessageAjax(returnMap, "0", "当前用户无权限！！！");
            return returnMap;
        }
        String officeId = UserUtils.getUser().getOffice().getId();
        if (gcsFlag) {
            pzCensus.setRestaurantId(officeId);
        }
        Page<PzCensus> page = pzCensusService.findCensusPage(new Page<>(request, response), pzCensus);
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }


}
