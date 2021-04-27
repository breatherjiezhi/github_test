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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzBoxCode")
public class PzBoxCodeController extends BaseController {

    @Autowired
    private PzBoxCodeService pzBoxCodeService;

    @ModelAttribute
    public PzBoxCode get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return pzBoxCodeService.get(id);
        }else {

            return new PzBoxCode();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzBoxCode pzBoxCode, HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzBoxCode/pzBoxCodeList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(PzBoxCode pzBoxCode, HttpServletRequest request, HttpServletResponse response){

        Page<PzBoxCode> page = pzBoxCodeService.findPage(new Page<>(request, response), pzBoxCode);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzBoxCode pzBoxCode,Model model){
        model.addAttribute("pzBoxCode", pzBoxCode);
        return "modules/pzBoxCode/pzBoxCodeForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzBoxCode pzBoxCode,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        //判断箱子编码是否唯一
        String boxCode = pzBoxCode.getBoxCode();
        if(StringUtils.isNotBlank(pzBoxCode.getId())){
            //不为空：修改
            PzBoxCode code = pzBoxCodeService.get(pzBoxCode.getId());
            String boxCodeNOUpdate = code.getBoxCode();
            if(!pzBoxCode.getBoxCode().equals(boxCodeNOUpdate)){
                Integer countByBoxCode = pzBoxCodeService.findCountByBoxCode(pzBoxCode.getBoxCode());
                if(countByBoxCode >0){
                    addMessageAjax(returnMap, "0", "箱子编码必须唯一，请重新填写");
                    return returnMap;
                }
            }
        }else {
            //为空：新增
            if(StringUtils.isNotBlank(boxCode)){
                Integer boxCodeCount =  pzBoxCodeService.findCountByBoxCode(boxCode);
                if(boxCodeCount >0){
                    addMessageAjax(returnMap, "0", "箱子编码必须唯一，请重新填写");
                    return returnMap;
                }
            }
        }


        if (ObjectUtils.isNotEmpty(pzBoxCode)) {
            flag =  pzBoxCodeService.saveOrUpdate(pzBoxCode);
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
        Integer flag = pzBoxCodeService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }


}
