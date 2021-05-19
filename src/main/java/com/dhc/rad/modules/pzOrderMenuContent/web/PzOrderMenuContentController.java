package com.dhc.rad.modules.pzOrderMenuContent.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzOrderMenuContent.entity.PzOrderMenuContent;
import com.dhc.rad.modules.pzOrderMenuContent.service.PzOrderMenuContentService;
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
public class PzOrderMenuContentController extends BaseController {

   @Autowired
   private PzOrderMenuContentService pzOrderMenuContentService;

    @ModelAttribute
    public PzOrderMenuContent get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return pzOrderMenuContentService.get(id);
        }else {
            return new PzOrderMenuContent();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzOrderMenuContent pzOrderMenuContent, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzOrderMenuContent/pzOrderMenuContentList";
    }

    @ResponseBody
    public Map<String,Object> searchPage(PzOrderMenuContent pzOrderMenuContent, HttpServletRequest request, HttpServletResponse response){

        Page<PzOrderMenuContent> page = pzOrderMenuContentService.findPage(new Page<>(request, response), pzOrderMenuContent);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzOrderMenuContent pzOrderMenuContent,Model model){
        model.addAttribute("pzOrderMenuContent", pzOrderMenuContent);
        return "modules/pzOrderMenuContent/pzOrderMenuContentForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzOrderMenuContent pzOrderMenuContent,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(pzOrderMenuContent)) {
            flag =  pzOrderMenuContentService.saveOrUpdate(pzOrderMenuContent);
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
        Integer flag = pzOrderMenuContentService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }
}
