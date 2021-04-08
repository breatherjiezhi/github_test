/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.act.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.act.entity.ModelEntity;
import com.dhc.rad.modules.act.service.ActModelService;
import com.google.common.collect.Maps;

/**
 * 流程模型相关Controller
 * @author DHC
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/model")
public class ActModelController extends BaseController {

	@Autowired
	private ActModelService actModelService;

	/**
	 * 流程模型列表
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = {"" })
	public String toModelList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {

//		Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
//				new Page<org.activiti.engine.repository.Model>(request, response), category);
//
//		model.addAttribute("page", page);
		model.addAttribute("category", category);

		return "modules/act/actModelList";
	}
	
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = { "searchPage"})
	@ResponseBody
	public Map<String,Object> modelList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<ModelEntity> page = actModelService.modelList(
				new Page<ModelEntity>(request, response), category);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;   
//		model.addAttribute("page", page);
//		model.addAttribute("category", category);
//
//		return "modules/act/actModelList";
	}
	/**
	 * 创建模型
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "toCreate", method = RequestMethod.GET)
	public String toCreate(Model model) {
		return "modules/act/actModelCreate";
	}
	
	/**
	 * 创建模型
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> create(String name, String key, String description, String category,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> returnMap = Maps.newHashMap();
		try {
			org.activiti.engine.repository.Model modelData = actModelService.create(name, key, description, category);
			returnMap.put("message","创建模型成功！");
			returnMap.put("id",modelData.getId());
			returnMap.put("messageStatus","1");
//			response.sendRedirect(request.getContextPath() + "/act/rest/service/editor?id=" + modelData.getId());
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建模型失败：", e);
			returnMap.put("messageStatus","0");
			returnMap.put("message","创建模型失败！");
			return returnMap;
		}
		
	}

	/**
	 * 根据Model部署流程
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "deploy")
	@ResponseBody
	public Map<String,Object> deploy(String id, RedirectAttributes redirectAttributes) {		
		Map<String,Object> returnMap = Maps.newHashMap();
		//actModelService.delete(id);
		String message = actModelService.deploy(id);
		returnMap.put("message",message);
		return returnMap;
	}
	
	/**
	 * 导出model的xml文件
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "export")
	public void export(String id, HttpServletResponse response) {
		actModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "updateCategory")
	public String updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
		actModelService.updateCategory(id, category);
		redirectAttributes.addFlashAttribute("message", "设置成功，模块ID=" + id);
		return "redirect:" + adminPath + "/act/model";
	}
	
	/**
	 * 删除Model
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("act:model:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object> delete(String id, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		actModelService.delete(id);
		returnMap.put("message", "删除成功，模型ID=" + id);
		return returnMap;
//		return "redirect:" + adminPath + "/act/model";
	}
}
