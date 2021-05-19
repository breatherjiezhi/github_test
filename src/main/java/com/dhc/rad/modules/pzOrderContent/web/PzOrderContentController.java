package com.dhc.rad.modules.pzOrderContent.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzOrderContent.entity.PzOrderContent;
import com.dhc.rad.modules.pzOrderContent.service.PzOrderContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/pzOrderContent")
public class PzOrderContentController extends BaseController {

   @Autowired
   private PzOrderContentService pzOrderContentService;

    @ModelAttribute
    public PzOrderContent get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return pzOrderContentService.get(id);
        }else {
            return new PzOrderContent();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzOrderContent PzOrderContent, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/PzOrderContent/PzOrderContentList";
    }

    @ResponseBody
    public Map<String,Object> searchPage(PzOrderContent PzOrderContent, HttpServletRequest request, HttpServletResponse response){

        Page<PzOrderContent> page = pzOrderContentService.findPage(new Page<>(request, response), PzOrderContent);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzOrderContent PzOrderContent,Model model){
        model.addAttribute("PzOrderContent", PzOrderContent);
        return "modules/PzOrderContent/PzOrderContentForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzOrderContent PzOrderContent,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(PzOrderContent)) {
            flag =  pzOrderContentService.saveOrUpdate(PzOrderContent);
        }
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = pzOrderContentService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }
}
