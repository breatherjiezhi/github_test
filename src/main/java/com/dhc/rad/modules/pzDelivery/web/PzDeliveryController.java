package com.dhc.rad.modules.pzDelivery.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;
import com.dhc.rad.modules.pzBoxCode.service.PzBoxCodeService;
import com.dhc.rad.modules.pzDelivery.entity.PzDelivery;
import com.dhc.rad.modules.pzDelivery.service.PzDeliveryService;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzDelivery")
public class PzDeliveryController extends BaseController {

    @Autowired
    private PzDeliveryService pzDeliveryService;

    @Autowired
    private PzBoxCodeService pzBoxCodeService;


    /**
     * @Description: 根据id查询配送信息
     * @Param: id
     * @return: PzDelivery
     * @Date: 2021/5/7
     */
    @ModelAttribute
    public PzDelivery get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return pzDeliveryService.get(id);
        } else {

            return new PzDelivery();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzDelivery pzDelivery, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/pzDelivery/pzDeliveryList";
    }

    /**
     * @Description: 根据条件查询所有配送数据
     * @Param: pzDelivery
     * @return: Map<String, Object>
     * @Date: 2021/5/7
     */
    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(PzDelivery pzDelivery, HttpServletRequest request, HttpServletResponse response) {

        Page<PzDelivery> page = pzDeliveryService.findPage(new Page<>(request, response), pzDelivery);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzDelivery pzDelivery, Model model) {
        model.addAttribute("pzDelivery", pzDelivery);
        return "modules/pzDelivery/pzDeliveryForm";
    }

    /**
     * @Description: 新增配送信息
     * @Param: pzDelivery
     * @return: Map<String, Object>
     * @Date: 2021/5/7
     */
    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String, Object> doSave(PzDelivery pzDelivery, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(pzDelivery)) {
            flag = pzDeliveryService.saveOrUpdate(pzDelivery);
        }
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }


    /**
     * @Description: 批量删除配送信息
     * @Param: ids
     * @return: Map<String, Object>
     * @Date: 2021/5/7
     */
    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = pzDeliveryService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }

}
