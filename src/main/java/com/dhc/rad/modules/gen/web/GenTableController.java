/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.web;


import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.gen.entity.GenTable;
import com.dhc.rad.modules.gen.service.GenTableService;
import com.dhc.rad.modules.gen.util.GenUtils;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典Controller
 * @author ZN
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {

    @Autowired
    private GenTableService genTableService;

    @ModelAttribute
    public GenTable get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return genTableService.get(id);
        } else {
            return new GenTable();
        }
    }

    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = {"index", ""})
    public String index(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genTable.setCreateBy(user);
        }
        List<String> nameList = genTableService.findNameList();
        model.addAttribute("nameList", nameList);

        return "modules/gen/genTableList";
    }


    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = {"list"})
    @ResponseBody
    public Map<String, Object> list(GenTable entity, HttpServletRequest request, HttpServletResponse response) {
        Page<GenTable> page = genTableService.findPage(new Page<GenTable>(request, response), entity);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = {"select"})
    @ResponseBody
    public Map<String, Object> select(GenTable genTable, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("genTable",genTableService.getTableFormDb(genTable));
        return returnMap;
    }

    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = "genTable")
    public String gen(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 获取物理表列表
        List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
        model.addAttribute("tableList", tableList);
        genTable.setName("act_hi_comment");
        genTable = genTableService.getTableFormDb(genTable);
        model.addAttribute("message", "");
        model.addAttribute("genTable", genTable);
        model.addAttribute("config", GenUtils.getConfig());
        return "modules/genTable/genTable";
    }

    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = "genTable2")
    public String gen2(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/genTable/genTable2";
    }


    @RequiresPermissions("gen:genTable:view")
    @RequestMapping(value = "form")
    public String form(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 获取物理表列表
        List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
        model.addAttribute("tableList", tableList);
        // 验证表是否存在
        if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
            model.addAttribute("message", "表已存在，请重新选表！");
            genTable.setName("");
            return "modules/gen/genTableForm";

        }
        // 获取物理表字段
        else {
            genTable = genTableService.getTableFormDb(genTable);
        }
        model.addAttribute("message", "");
        model.addAttribute("genTable", genTable);
        model.addAttribute("config", GenUtils.getConfig());
        return "modules/gen/genTableForm";
    }

    @RequiresPermissions("gen:genTable:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (!beanValidator(model, genTable)) {
            addMessageAjax(returnMap, Global.ERROR, "保存失败！");
            return returnMap;
        }
        // 验证表是否已经存在
        if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
            addMessageAjax(returnMap, Global.ERROR, "保存失败！" + genTable.getName() + " 表已经存在！");
            genTable.setName("");
            return returnMap;
        }
        genTableService.save(genTable);
        addMessageAjax(returnMap, Global.SUCCESS, "保存业务表'" + genTable.getName() + "'成功");
        return returnMap;
    }

    @RequiresPermissions("gen:genTable:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "ids", required = false) String[] ids) {

        Map<String, Object> returnMap = Maps.newHashMap();

        try {
            boolean b = genTableService.delete(ids);
            if (b) {
                addMessageAjax(returnMap, Global.SUCCESS, "删除业务表成功");
            } else {
                addMessageAjax(returnMap, Global.ERROR, "删除业务表失败");
            }
        } catch (Exception e) {
            logger.error("Exception:{}", e.getMessage());
            addMessageAjax(returnMap, Global.ERROR, "系统异常");
        }
        return returnMap;
    }


}