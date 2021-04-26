package com.dhc.rad.modules.pzOrder.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzOrder.service.PzOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzOrder")
public class PzOrderController extends BaseController {

    @Autowired
    private PzOrderService pzOrderService;

    /**
     * @Description: 根据订单id获取订单信息
     * @Param: id
     * @return: PzOrder
     * @Author: zhengXiang
     * @Date: 2021/4/22
     */
    @ModelAttribute
    public PzOrder get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return pzOrderService.get(id);
        } else {
            return new PzOrder();
        }
    }
    /**
    * @Description: 查所有订单信息
    * @Param: pzOrder
    * @return: 页面跳转到pzOrderList.jsp页面
    * @Author: zhengXiang
    * @Date: 2021/4/22
    */
    @RequestMapping(value = {"list"})
    public String list(PzOrder pzOrder, HttpServletRequest request, HttpServletResponse response, Model model){
        List<PzOrder> pzOrderList = pzOrderService.findList(pzOrder);
        model.addAttribute("pzOrderList", pzOrderList);
        return "modules/pzOrder/pzOrderList";
    }

    /**
    * @Description: 根据条件分页查询
    * @Param: pzOrder
    * @return: Map<String,Object>
    * @Author: zhengXiang
    * @Date: 2021/4/22
    */
    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(PzOrder pzOrder, HttpServletRequest request, HttpServletResponse response){
        Page<PzOrder> page = pzOrderService.findPage(new Page<>(request, response), pzOrder);

        Map<String,Object> returnMap = new HashMap<>();

        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());

        return returnMap;
    }

    /**
    * @Description:  表单提交数据
    * @Param:  pzOrder
    * @return: 页面跳转pzOrderForm.jsp页面
    * @Author: zhengXiang
    * @Date: 2021/4/22
    */
    @RequestMapping(value = "form")
    public String form(PzOrder pzOrder, Model model){

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String localAddr = request.getLocalAddr();
        int serverPort = request.getServerPort();
        model.addAttribute("httpUrl", "http://"+localAddr +":"+ serverPort+ File.separator);
        model.addAttribute("pzOrder", pzOrder);
        return "modules/pzOrder/pzOrderForm";
    }

    /**
    * @Description: 批量删除
    * @Param: ids
    * @return: Map<String, Object>
    * @Author: zhengXiang
    * @Date: 2021/4/22
    */
    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        List<String> idList = Arrays.asList(ids.split(","));
        Integer flag = pzOrderService.deleteByIds(idList);
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }


}
