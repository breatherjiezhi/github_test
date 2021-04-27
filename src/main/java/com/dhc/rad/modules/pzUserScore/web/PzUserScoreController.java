package com.dhc.rad.modules.pzUserScore.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;
import com.dhc.rad.modules.pzUserScore.service.PzUserScoreService;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzUserScore")
public class PzUserScoreController extends BaseController {

    @Autowired
    private PzUserScoreService pzUserScoreService;

    @ModelAttribute
    public PzUserScore get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return pzUserScoreService.get(id);
        }else {

            return new PzUserScore();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzUserScore pzUserScore, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzUserScore/pzUserScoreList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(PzUserScore pzUserScore, HttpServletRequest request, HttpServletResponse response){

        Page<PzUserScore> page = pzUserScoreService.findPage(new Page<>(request, response), pzUserScore);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzUserScore pzUserScore,Model model){
        model.addAttribute("pzUserScore", pzUserScore);
        return "modules/pzUserScore/pzUserScoreForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzUserScore pzUserScore,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(pzUserScore)) {
            flag =  pzUserScoreService.saveOrUpdate(pzUserScore);
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
        Integer flag = pzUserScoreService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }
}
