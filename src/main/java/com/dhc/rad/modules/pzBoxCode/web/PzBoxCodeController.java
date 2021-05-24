package com.dhc.rad.modules.pzBoxCode.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;
import com.dhc.rad.modules.pzBoxCode.service.PzBoxCodeService;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzBoxCode")
public class PzBoxCodeController extends BaseController {

    @Autowired
    private PzBoxCodeService pzBoxCodeService;


    @RequestMapping(value = {"list"})
    public String list(PzBoxCode pzBoxCode, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzBoxCode/pzBoxCodeList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(PzBoxCode pzBoxCode, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> returnMap = new HashMap<>();

        if (!UserUtils.getRoleFlag("admin") && !UserUtils.getRoleFlag("admins")) {
            pzBoxCode.setRestaurantId(UserUtils.getUser().getOffice().getId());
        }
        Page<PzBoxCode> page = pzBoxCodeService.findPage(new Page<>(request, response), pzBoxCode);

        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzBoxCode pzBoxCode,Model model){
        PzBoxCode pc= pzBoxCodeService.get(pzBoxCode.getId());
        if(pc!=null){
            model.addAttribute("pzBoxCode", pc);
        }else{
            model.addAttribute("pzBoxCode", new PzBoxCode());
        }
        if(UserUtils.getRoleFlag("gcs")){
            model.addAttribute("officeId", UserUtils.getUser().getOffice().getId());
        }else{
            model.addAttribute("officeId", "");
        }

        return "modules/pzBoxCode/pzBoxCodeForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzBoxCode pzBoxCode,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        if(pzBoxCode==null){
            addMessageAjax(returnMap, "0", "保存失败");
            return returnMap;
        }
        //判断当前用户是否和传入的restaurantId是否为同一家
        String officeId = UserUtils.getUser().getOffice().getId();
        if(!pzBoxCode.getRestaurantId().equals(officeId)){
            addMessageAjax(returnMap, "0", "选择其他餐厅信息无效，请重新选择");
            return returnMap;
        }

        //判断箱子编码是否唯一
        String boxCode = pzBoxCode.getBoxCode();
        String restaurantId = pzBoxCode.getRestaurantId();
        if(StringUtils.isNotBlank(boxCode)&&StringUtils.isNotBlank(restaurantId) ){

            boxCode = String.format("%03d", Integer.parseInt(boxCode));
            pzBoxCode.setBoxCode(boxCode);

            PzBoxCode pzFlag =  pzBoxCodeService.findByBoxCode(pzBoxCode.getId(),boxCode,restaurantId);

            if(pzFlag!=null){
                addMessageAjax(returnMap, "0", "箱子编码必须唯一，请重新填写");
                return returnMap;
            }else{
                Integer flag  =  pzBoxCodeService.saveOrUpdate(pzBoxCode);
                if (flag > 0) {
                    addMessageAjax(returnMap, "1", "保存成功");
                    return returnMap;
                } else {
                    addMessageAjax(returnMap, "0", "保存失败");
                    return returnMap;
                }

            }
        }else{
            addMessageAjax(returnMap, "0", "保存失败");
            return returnMap;
        }
    }

    @RequestMapping(value = "deleteByIds", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = pzBoxCodeService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }

    @RequestMapping(value = {"test"})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model){
        // 数据封装主map
        Map<String, Object> orderStatisticMap = new HashMap<>();
        // 取餐点
        orderStatisticMap.put("areaLocation", "新区01号");
        // 服务单元
        orderStatisticMap.put("serviceUnitName", "电气研发1");
        // 餐厅名称
        orderStatisticMap.put("restaurantName", "绿泉");
        // 二维码参数
        orderStatisticMap.put("qrCode", "www.baidu.com");
        // 套餐数量统计list
        List<Map<String, Object>> menuStatisticlist = new ArrayList<>();
        // 套餐数量统计明细
        Map<String, Object> menuStatisticMap1 = new HashMap<>();
        menuStatisticMap1.put("menuName", "A");
        menuStatisticMap1.put("menuNum", 5);
        Map<String, Object> menuStatisticMap2 = new HashMap<>();
        menuStatisticMap2.put("menuName", "B");
        menuStatisticMap2.put("menuNum", 6);
        Map<String, Object> menuStatisticMap3 = new HashMap<>();
        menuStatisticMap3.put("menuName", "C");
        menuStatisticMap3.put("menuNum", 8);
        menuStatisticlist.add(menuStatisticMap1);
        menuStatisticlist.add(menuStatisticMap2);
        menuStatisticlist.add(menuStatisticMap3);
        orderStatisticMap.put("menuStatisticlist", menuStatisticlist);
        // 订单明细list
        List<Map<String, Object>> orderDetaillist = new ArrayList<>();
        // 订单明细
        Map<String, Object> orderDetailMap1 = new HashMap<>();
        orderDetailMap1.put("userName", "张三");
        orderDetailMap1.put("menuName", "A");
        orderDetailMap1.put("menuDetail", "胡萝卜");
        Map<String, Object> orderDetailMap2 = new HashMap<>();
        orderDetailMap2.put("userName", "李四");
        orderDetailMap2.put("menuName", "B");
        orderDetailMap2.put("menuDetail", "黄焖鸡米饭");
        Map<String, Object> orderDetailMap3 = new HashMap<>();
        orderDetailMap3.put("userName", "张三");
        orderDetailMap3.put("menuName", "C");
        orderDetailMap3.put("menuDetail", "大米饭");
        orderDetaillist.add(orderDetailMap1);
        orderDetaillist.add(orderDetailMap2);
        orderDetaillist.add(orderDetailMap3);
        for (int i = 0; i<1; i++) {
            Map<String, Object> orderDetailMap666 = orderDetailMap3;
            orderDetaillist.add(orderDetailMap666);
        }
        orderStatisticMap.put("orderDetaillist", orderDetaillist);
        List<Map<String, Object>> orderStatisticlist = new ArrayList<>();
        for (int i = 0; i<18; i++) {
            Map<String, Object> orderStatisticMap1 = orderStatisticMap;
            orderStatisticlist.add(orderStatisticMap1);
        }
        model.addAttribute("orderStatisticlist", orderStatisticlist);
        return "modules/test/printTest";
    }
}
