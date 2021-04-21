package com.dhc.rad.modules.pzMenuFile.web;

import com.alibaba.fastjson.JSONObject;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.FileUtil;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenuFile.entity.PzMenuFile;
import com.dhc.rad.modules.pzMenuFile.service.PzMenuFileService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@RequestMapping(value = "${adminPath}/pzMenuFile")
public class PzMenuFileController extends BaseController {

    @Autowired
    private PzMenuFileService pzMenuFileService;

    @ModelAttribute
    public PzMenuFile get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return pzMenuFileService.get(id);
        } else {

            return new PzMenuFile();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzMenuFile pzMenuFile, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<PzMenuFile> pzMenuFileList = pzMenuFileService.findList(pzMenuFile);
        model.addAttribute("pzMenuFileList", pzMenuFileList);
        return "modules/pzMenuFile/pzMenuFileList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(PzMenuFile pzMenuFile, HttpServletRequest request, HttpServletResponse response) {

        Page<PzMenuFile> page = pzMenuFileService.findPage(new Page<>(request, response), pzMenuFile);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzMenuFile pzMenuFile, Model model) {
        model.addAttribute("pzMenuFile", pzMenuFile);
        return "modules/pzMenuFile/pzMenuFileForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String, Object> doSave(PzMenuFile pzMenuFile, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(pzMenuFile)) {
            flag = pzMenuFileService.saveOrUpdate(pzMenuFile);
        }
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = pzMenuFileService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }

    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> resultMap = Maps.newHashMap();
        String ctxPath = request.getSession().getServletContext().getRealPath("");
        PzMenuFile pzMenuFile= pzMenuFileService.pzMenFileUpload(file, ctxPath);
        returnMap.put("pzMenuFile", pzMenuFile);
        returnMap.put("code", 0);
        return returnMap;
    }
}
