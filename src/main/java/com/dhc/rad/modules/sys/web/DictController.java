/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.jqgridSearch.JqGridHandler;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.Dict;
import com.dhc.rad.modules.sys.service.DictService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典Controller
 * @author DHC
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public Dict get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return dictService.get(id);
		}else{
			return new Dict();
		}
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
		return "modules/sys/dictList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Dict dict,HttpServletRequest request, HttpServletResponse response) {
		String where = new JqGridHandler(request).getWheres(null, true);
		System.out.println("sql:　　　　　　"+where);
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response),dict); 
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}

	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/dictForm";
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public  Map<String,Object> save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			break out;
//			return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		}else if (!beanValidatorAjax(returnMap,dict)){
	//		return form(dict, model);
			break out;	 
		}else{	
			dictService.save(dict);
			addMessageAjax(returnMap,"1","保存字典'" + dict.getLabel() + "'成功");
		}
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
		return returnMap;
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public  Map<String,Object> delete(Dict dict, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/dict/?repage";
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			dictService.delete(dict);
			addMessageAjax(returnMap,"1", "删除字典成功");
		}
		return returnMap;
//		return "redirect:" + adminPath + "/sys/dict/?repage&type="+dict.getType();
	}

	/**
	 * 函数功能说明 :批量删除字典
	 * 作者: fangzr  
	 * 创建时间：2015-11-3
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param ids
	 * @参数： @param redirectAttributes
	 * @参数： @return
	 * @throws
	 */
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public  Map<String,Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{

			dictService.batchDelete(ids);
			addMessageAjax(returnMap,"1", "删除字典成功");
		}
		return returnMap;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Dict dict = new Dict();
		dict.setType(type);
		List<Dict> list = dictService.findList(dict);
		for (int i=0; i<list.size(); i++){
			Dict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<Dict> listData(@RequestParam(required=false) String type) {
		Dict dict = new Dict();
		dict.setType(type);
		return dictService.findList(dict);
	}

}
