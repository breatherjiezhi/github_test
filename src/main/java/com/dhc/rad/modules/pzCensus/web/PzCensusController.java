package com.dhc.rad.modules.pzCensus.web;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.utils.excel.ExportExcel;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzCensus.entity.PzCensus;
import com.dhc.rad.modules.pzCensus.service.PzCensusService;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.activiti.explorer.util.time.timeunit.WeekTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/pzCensus")
public class PzCensusController extends BaseController {

    @Autowired
    private PzCensusService pzCensusService;

    @Autowired
    private OfficeService officeService;


    @RequestMapping(value = {"list"})
    public String list(PzCensus pzCensus, HttpServletRequest request, HttpServletResponse response, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String eatDate = sdf.format(new Date());
        pzCensus.setEatDate(eatDate);
        model.addAttribute("officeId", UserUtils.getUser().getOffice().getId());
        model.addAttribute("roleFlag", !(UserUtils.getRoleFlag("admin") || UserUtils.getRoleFlag("admins")));
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

        List<PzCensus> list = pzCensusService.findCensusSum(pzCensus);
        if (list.size() > 0) {
            returnMap.put("totalA", list.get(0).getCountA());
            returnMap.put("totalB", list.get(0).getCountB());
            returnMap.put("totalC", list.get(0).getCountC());
            returnMap.put("totalD", list.get(0).getCountD());
            returnMap.put("totalE", list.get(0).getCountE());
            returnMap.put("totalF", list.get(0).getCountF());
        } else {
            returnMap.put("totalA", 0);
            returnMap.put("totalB", 0);
            returnMap.put("totalC", 0);
            returnMap.put("totalD", 0);
            returnMap.put("totalE", 0);
            returnMap.put("totalF", 0);
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
        List<PzCensus> list = pzCensusService.findCensusSum(pzCensus);
        if (list.size() > 0) {
            returnMap.put("totalA", list.get(0).getCountA());
            returnMap.put("totalB", list.get(0).getCountB());
            returnMap.put("totalC", list.get(0).getCountC());
            returnMap.put("totalD", list.get(0).getCountD());
            returnMap.put("totalE", list.get(0).getCountE());
            returnMap.put("totalF", list.get(0).getCountF());
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



    @RequestMapping(value = {"downUserCensus"})
    public String downUserCensus(PzCensus pzCensus, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        String officeId = pzCensus.getServiceUnitId();
        String beginDate = pzCensus.getBeginDate();
        String endDate = pzCensus.getEndDate();

        String restaurantId = null;

        String title = "";
        String fileName ="";

        if(StringUtils.isNotBlank(officeId)){
            String officeName =  officeService.get(officeId) !=null ? officeService.get(officeId).getName():"";
            title+=officeName+"_";
            fileName+=officeName+"_";
        }

        if(!(UserUtils.getRoleFlag("admin")||UserUtils.getRoleFlag("admins"))){
            restaurantId = UserUtils.getUser().getOffice().getId();
            String  restaurantName = officeService.get(restaurantId) !=null ? officeService.get(restaurantId).getName():"";
            title+=restaurantName+"_";
            fileName+=restaurantName+"_";
        }

        List<String> eadDateList = pzCensusService.findEatDate(beginDate,endDate);


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String nowDate = sdf.format(new Date());

            fileName += beginDate+"至"+endDate+"订餐统计汇总"+nowDate+".xlsx";
            title+=beginDate+"至"+endDate+"订餐统计";

            //表格获取列数据使用key值
            List<String> headerKeyList = new ArrayList<>();

            //表格列头名称
            List<String> headerList = new ArrayList<>();
            headerList.add("服务单元");
            headerKeyList.add("serviceUnitName");
            headerList.add("取餐点");
            headerKeyList.add("areaName");
            if(UserUtils.getRoleFlag("admin")||UserUtils.getRoleFlag("admins")){
                headerList.add("工号");
                headerKeyList.add("userNo");
                headerList.add("姓名");
                headerKeyList.add("userName");
            }
            headerList.add("餐饮公司");
            headerKeyList.add("restaurantName");
            for (String eatDate : eadDateList) {
                headerList.add(eatDate);
                headerKeyList.add(eatDate);
            }
            List<Map<String,Object>> list = pzCensusService.findUserCensusPage(restaurantId,officeId,beginDate,endDate,null,null);
            new ExportExcel(title,headerList).setMapDataList(list,headerKeyList).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "下载失败！失败信息：" + e.getMessage());
        }
        return null;
    }



    @RequestMapping(value = {"downDeptCensus"})
    public String downDeptCensus(PzCensus pzCensus, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        String officeId = pzCensus.getServiceUnitId();
        String beginDate = pzCensus.getBeginDate();
        String endDate = pzCensus.getEndDate();

        String restaurantId = null;

        String title = "";
        String fileName ="";

        if(StringUtils.isNotBlank(officeId)){
            String officeName =  officeService.get(officeId) !=null ? officeService.get(officeId).getName():"";
            title+=officeName+"_";
            fileName+=officeName+"_";
        }


        if(!(UserUtils.getRoleFlag("admin")||UserUtils.getRoleFlag("admins"))){
            restaurantId = UserUtils.getUser().getOffice().getId();
            String  restaurantName = officeService.get(restaurantId) !=null ? officeService.get(restaurantId).getName():"";
            title+=restaurantName+"_";
            fileName+=restaurantName+"_";
        }

        List<String> eadDateList = pzCensusService.findEatDate(beginDate,endDate);


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String nowDate = sdf.format(new Date());

            fileName += beginDate+"至"+endDate+"订餐结算汇总"+nowDate+".xlsx";
            title+=beginDate+"至"+endDate+"订餐结算统计";

            //表格获取列数据使用key值
            List<String> headerKeyList = new ArrayList<>();

            //表格列头名称
            List<String> headerList = new ArrayList<>();
            headerList.add("部门名称");
            headerKeyList.add("serviceUnitName");
            headerList.add("餐饮公司");
            headerKeyList.add("restaurantName");
            for (String eatDate : eadDateList) {
                headerList.add(eatDate);
                headerKeyList.add(eatDate);
            }
            List<Map<String,Object>> list = pzCensusService.findDeptCensusPage(restaurantId,officeId,beginDate,endDate,null,null);
            new ExportExcel(title,headerList).setMapDataList(list,headerKeyList).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "下载失败！失败信息：" + e.getMessage());
        }
        return null;
    }


}
