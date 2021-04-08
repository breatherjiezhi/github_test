/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.act.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.act.entity.DeployProcess;
import com.dhc.rad.modules.act.entity.ProcessInstanceEntity;
import com.dhc.rad.modules.act.service.ActProcessService;
import com.google.common.collect.Maps;

/**
 * 流程定义相关Controller
 * @author DHC
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/process")
public class ActProcessController extends BaseController {

	@Autowired
	private ActProcessService actProcessService;

	/**
	 * 跳到流程定义列表
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = {"toProcessList", ""})
	public String toProcessList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("category", category);
		return "modules/act/actProcessList";
	}

	
	/**
	 * 流程定义列表
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
		Page<DeployProcess> page = actProcessService.processList(new Page<DeployProcess> (request, response), category);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;      
	}
	
	
	/**
	 * 转向运行中的页面
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "torunning")
	public String toRunningList(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("procInsId", procInsId);
		model.addAttribute("procDefKey", procDefKey);
		return "modules/act/actProcessRunningList";
	}

	/**
	 * 运行中的实例列表
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "runningList")
	@ResponseBody
	public Map<String,Object> runningList(String procInsId, String procDefKey, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<ProcessInstanceEntity> page = actProcessService.runningList(new Page<ProcessInstanceEntity>(request, response), procInsId, procDefKey);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;     
	}

	
	/**
	 * 读取资源，通过部署ID
	 * @param processDefinitionId  流程定义ID
	 * @param processInstanceId 流程实例ID
	 * @param resourceType 资源类型(xml|image)
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "resource/read")
	public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
		InputStream resourceAsStream = actProcessService.resourceRead(procDefId, proInsId, resType);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 部署流程
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "/deploy", method=RequestMethod.GET)
	public String deploy(Model model) {
		return "modules/act/actProcessDeploy";
	}
	
	/**
	 * 部署流程 - 保存
	 * @param file
	 * @return
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "/deploy", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deploy(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir, 
			String category, MultipartFile file, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();

		String fileName = file.getOriginalFilename();
		
		if (StringUtils.isBlank(fileName)){
			returnMap.put("message",  "请选择要部署的流程文件");
		}else{
			String message = actProcessService.deploy(exportDir, category, file);
			returnMap.put("message",  message);
		}

		return returnMap;
	}
	
	/**
	 * 设置流程分类
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "updateCategory")
	public String updateCategory(String procDefId, String category, RedirectAttributes redirectAttributes) {
		actProcessService.updateCategory(procDefId, category);
		return "redirect:" + adminPath + "/act/process";
	}

	/**
	 * 挂起、激活流程实例
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "update/{state}")
	@ResponseBody
	public Map<String,Object> updateState(@PathVariable("state") String state, String procDefId, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		String message = actProcessService.updateState(state, procDefId);
		addMessageAjax(returnMap,"1", message);
		return returnMap;

//		redirectAttributes.addFlashAttribute("message", message);
//		return "redirect:" + adminPath + "/act/process";
	}
	
	/**
	 * 将部署的流程转换为模型
	 * @param procDefId
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "convert/toModel")
	@ResponseBody
	public Map<String,Object>  convertToModel(String procDefId, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, XMLStreamException {
		Map<String,Object> returnMap = Maps.newHashMap();
		org.activiti.engine.repository.Model modelData = actProcessService.convertToModel(procDefId);
		addMessageAjax(returnMap,"1", "转换模型成功，模型ID="+modelData.getId());
		return returnMap;
//		return "redirect:" + adminPath + "/act/model";
	}
	
	/**
	 * 导出图片文件到硬盘
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "export/diagrams")
	@ResponseBody
	public List<String> exportDiagrams(@Value("#{APP_PROP['activiti.export.diagram.path']}") String exportDir) throws IOException {
		List<String> files = actProcessService.exportDiagrams(exportDir);;
		return files;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * @param deploymentId 流程部署ID
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String,Object>  delete(String deploymentId) {
		Map<String,Object> returnMap = Maps.newHashMap();
		actProcessService.deleteDeployment(deploymentId);
		addMessageAjax(returnMap,"1", "删除字典成功");
		return returnMap;
//		return "redirect:" + adminPath + "/act/process";
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteProcIns")
	public Map<String,Object> deleteProcIns(String procInsId, String reason, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		out:
		if (StringUtils.isBlank(reason)){
			addMessageAjax(returnMap,"1","请填写删除原因");
			break out;
		}else{
			actProcessService.deleteProcIns(procInsId, reason);
			addMessageAjax(returnMap,"1","删除流程实例成功，实例ID=" + procInsId);
			break out;
		}	
		return returnMap;
//		return "redirect:" + adminPath + "/act/process/running/";
	}
	
}
