/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.kaoqin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.kaoqin.entity.AomKaoqin;
import com.dhc.rad.modules.kaoqin.service.AomKaoqinService;

/**
 * 考勤信息Controller
 * @author fangzr
 * @version 2015-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/kaoqin/aomKaoqin")
public class AomKaoqinController extends BaseController {

	@Autowired
	private AomKaoqinService aomKaoqinService;
	
	@ModelAttribute
	public AomKaoqin get(@RequestParam(required=false) String id) {
		AomKaoqin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = aomKaoqinService.get(id);
		}
		if (entity == null){
			entity = new AomKaoqin();
		}
		return entity;
	}

	@RequestMapping(value = {"widgets", ""})
	public String widgets(AomKaoqin aomKaoqin, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/sys/sysWidgets";
	}

	
	@RequiresPermissions("kaoqin:aomKaoqin:view")
	@RequestMapping(value = {"list", ""})
	public String list(AomKaoqin aomKaoqin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AomKaoqin> page = aomKaoqinService.findPage(new Page<AomKaoqin>(request, response), aomKaoqin); 
		model.addAttribute("page", page);
		return "modules/kaoqin/aomKaoqinList";
	}

	@RequiresPermissions("kaoqin:aomKaoqin:view")
	@RequestMapping(value = "form")
	public String form(AomKaoqin aomKaoqin, Model model) {
		model.addAttribute("aomKaoqin", aomKaoqin);
		return "modules/kaoqin/aomKaoqinForm";
	}

	@RequiresPermissions("kaoqin:aomKaoqin:edit")
	@RequestMapping(value = "save")
	public String save(AomKaoqin aomKaoqin, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, aomKaoqin)){
			return form(aomKaoqin, model);
		}
		aomKaoqinService.save(aomKaoqin);
		addMessage(redirectAttributes, "保存考勤信息成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqin/aomKaoqin/?repage";
	}
	
	@RequiresPermissions("kaoqin:aomKaoqin:edit")
	@RequestMapping(value = "delete")
	public String delete(AomKaoqin aomKaoqin, RedirectAttributes redirectAttributes) {
		aomKaoqinService.delete(aomKaoqin);
		addMessage(redirectAttributes, "删除考勤信息成功");
		return "redirect:"+Global.getAdminPath()+"/kaoqin/aomKaoqin/?repage";
	}

}