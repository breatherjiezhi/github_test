package com.dhc.rad.common.quartz.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.quartz.entity.ScheduleJob;
import com.dhc.rad.common.quartz.service.ScheduleJobService;
import com.dhc.rad.common.web.BaseController;
import com.google.common.collect.Maps;

/**
 * 定时任务配置 Controller
 * 
 * @author hj
 * @version 2016-10-8
 */
@Controller
@RequestMapping(value = "${adminPath}/system/scheduleJob")
public class ScheduleJobController extends BaseController {

	@Autowired
	private ScheduleJobService scheduleJobService;

	/*
	 * 进入list页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model,ScheduleJob entity) {
		
		
		return "quartz/quartz";
	}


	/*
	 * 获取list页面数据
	 */
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(ScheduleJob entity,HttpServletRequest request, HttpServletResponse response) {
		Page<ScheduleJob> page = scheduleJobService.findStore(new Page<ScheduleJob>(request, response), entity);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
		return returnMap;
	}


	/*
	 * 新增、编辑
	 */
	@RequiresPermissions("sys:quartz:scheduleJob")
	@RequestMapping(value = "form")
	public String form(ScheduleJob scheduleJob,HttpServletRequest request, Model model) {
		if(scheduleJob.getJobId()!=null && !"".equals(scheduleJob.getJobId().toString())){
			scheduleJob = scheduleJobService.getTaskById(scheduleJob.getJobId());
		}
		model.addAttribute("scheduleJob",scheduleJob);
		
		return "quartz/quartzForm";
	}
	
	/*
	 * 保存
	 */
	@RequiresPermissions("sys:quartz:scheduleJob")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(ScheduleJob scheduleJob, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<>();
		// 保存库房信息
		scheduleJobService.insert(scheduleJob);
		addMessageAjax(returnMap, "1",  "保存任务'" + scheduleJob.getJobId() + "'成功");
		return returnMap;
	}
	  
	/*
	 * 编辑保存
	 */
    @RequiresPermissions("sys:quartz:scheduleJob")
	@RequestMapping(value = "edit")
	@ResponseBody
	public Map<String,Object> doUpdate(ScheduleJob scheduleJob, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<>();
        
		// 保存库房信息
		scheduleJobService.update(scheduleJob);
		addMessageAjax(returnMap, "1",  "保存任务'" + scheduleJob.getJobId() + "'成功");
		return returnMap;
	}
		
    /*
	 * 保存并启动
	 */
    
    
    
    /*
	 * 启动任务，区别于新增，启用后任务添加的执行列表中，新增未必启用
	 */
    @RequiresPermissions("sys:quartz:scheduleJob")
	@RequestMapping(value = "addJob")
	@ResponseBody
	public  Map<String,Object> addJob(String[] ids, RedirectAttributes redirectAttributes) throws SchedulerException {
		Map<String,Object> returnMap = Maps.newHashMap();
		List<String> list = Arrays.asList(ids);
		ScheduleJob scheduleJob =  scheduleJobService.getTaskById(Long.parseLong(list.get(0)));
		scheduleJob.setJobStatus("1");
		scheduleJobService.addJob(scheduleJob);
		
		addMessageAjax(returnMap,"1", "启用成功");
		return returnMap;
	}
    
    
	/*
	 * 批量删除任务
	 */
    @RequiresPermissions("sys:quartz:scheduleJob")
	@RequestMapping(value = "doDelete")
	@ResponseBody
	public  Map<String,Object> doDelete(String[] ids, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		
		List<String> list = Arrays.asList(ids);
		
		scheduleJobService.doDeleteUser(list);
		addMessageAjax(returnMap,"1", "删除任务成功");
		return returnMap;
	}
	  	
    	
    /*
	 * 立即运行一次
	 */
    @RequestMapping(value = "runJobNow")
	@ResponseBody
	public  Map<String,Object> runJobNow(Long jobId, RedirectAttributes redirectAttributes) throws SchedulerException {
		Map<String,Object> returnMap = Maps.newHashMap();
		ScheduleJob scheduleJob = new ScheduleJob();
		if(jobId!=null && !"".equals(String.valueOf(jobId))){
			scheduleJob = scheduleJobService.getTaskById(jobId);
		}
		scheduleJobService.runAJobNow(scheduleJob);
		addMessageAjax(returnMap,"1", "运行成功");
		return returnMap;
	}
    
    /*
	 * 恢复暂停执行的任务
	 */
    @RequestMapping(value = "startTask")
	@ResponseBody
	public  Map<String,Object> startTask(Long jobId, RedirectAttributes redirectAttributes) throws SchedulerException {
		Map<String,Object> returnMap = Maps.newHashMap();
		ScheduleJob scheduleJob = new ScheduleJob();
		if(jobId!=null && !"".equals(String.valueOf(jobId))){
			scheduleJob = scheduleJobService.getTaskById(jobId);
		}
		scheduleJobService.resumeJob(scheduleJob);
		addMessageAjax(returnMap,"1", "设置成功，任务已恢复执行！");
		return returnMap;
	}
    
    /*
	 * 暂停执行中的任务
	 */
    @RequestMapping(value = "stopTask")
	@ResponseBody
	public  Map<String,Object> stopTask(Long jobId, RedirectAttributes redirectAttributes) throws SchedulerException {
		Map<String,Object> returnMap = Maps.newHashMap();
		ScheduleJob scheduleJob = new ScheduleJob();
		if(jobId!=null && !"".equals(String.valueOf(jobId))){
			scheduleJob = scheduleJobService.getTaskById(jobId);
		}
		scheduleJobService.pauseJob(scheduleJob);
		addMessageAjax(returnMap,"1", "设置成功，任务已暂停！");
		return returnMap;
	}
    
    /*
	 * 删除计划执行的任务
	 */
    @RequestMapping(value = "deleteTask")
	@ResponseBody
	public  Map<String,Object> deleteTask(Long jobId, RedirectAttributes redirectAttributes) throws SchedulerException {
		Map<String,Object> returnMap = Maps.newHashMap();
		ScheduleJob scheduleJob = new ScheduleJob();
		if(jobId!=null && !"".equals(String.valueOf(jobId))){
			scheduleJob = scheduleJobService.getTaskById(jobId);
		}
		scheduleJobService.deleteJob(scheduleJob);
		addMessageAjax(returnMap,"1", "设置成功，任务已删除！");
		return returnMap;
	}
}