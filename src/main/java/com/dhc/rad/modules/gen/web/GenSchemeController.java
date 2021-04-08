/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.web;


import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.gen.entity.GenScheme;
import com.dhc.rad.modules.gen.service.GenSchemeService;
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
@RequestMapping(value = "${adminPath}/gen/genScheme")
public class GenSchemeController extends BaseController {

	@Autowired
	private GenSchemeService genSchemeService;
	@Autowired
	private GenTableService genTableService;



	@ModelAttribute
	public GenScheme get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genSchemeService.get(id);
		}else{
			return new GenScheme();
		}
	}

	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = { "index", "" })
	public String index(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user= UserUtils.getUser();
		if (!user.isAdmin()){
			genScheme.setCreateBy(user);
		}
		List<String> nameList =genSchemeService.findNameList() ;
		model.addAttribute("nameList", nameList);
		return "modules/gen/genSchemeList";
	}
	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = { "list" })
	@ResponseBody
	public Map<String, Object> list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response) {
		Page<GenScheme> page = genSchemeService.findPage(new Page<GenScheme>(request, response), genScheme);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;

	}


	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = "form")
	public String form(GenScheme genScheme, Model model){
		if (StringUtils.isBlank(genScheme.getPackageName())){
			genScheme.setPackageName("com.dhc.rad.modules");
		}
		model.addAttribute("genScheme",genScheme);
		model.addAttribute("config", GenUtils.getConfig());
		model.addAttribute("tableList",genTableService.findAll());

		return "modules/gen/genSchemeForm";

	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(GenScheme genScheme, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (!beanValidator(model, genScheme)){
			addMessageAjax(returnMap, "0", "验证失败！");
			return returnMap;
		}
		String result = genSchemeService.save(genScheme);
		addMessageAjax(returnMap, "1", "操作生成方案'" + genScheme.getName() + "'成功<br/>"+result);
		return returnMap;
	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "ids",required = false) String[] ids) {

		Map<String, Object> returnMap = Maps.newHashMap();

		try {
			boolean b = genSchemeService.delete(ids);
			if (b) {
				addMessageAjax(returnMap, "1", "删除方案成功");
			}else{
				addMessageAjax(returnMap, "0", "删除方案失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessageAjax(returnMap, Global.ERROR, "系统异常");
		}
		return returnMap;
	}





}