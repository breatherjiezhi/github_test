/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.DateUtils;
import com.dhc.rad.common.utils.jqgridSearch.JqGridHandler;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.Dict;
import com.dhc.rad.modules.sys.entity.Log;
import com.dhc.rad.modules.sys.service.LogService;

/**
 * 日志Controller
 * @author DHC
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date beginDate = DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", DateUtils.addMonths(beginDate, 1));
		return "modules/sys/logList";
	}

	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Log log,HttpServletRequest request, HttpServletResponse response) {
		String where = new JqGridHandler(request).getWheres(null, true);
		System.out.println("sql:　　　　　　"+where);
        Page<Log> page = logService.findPage(new Page<Log>(request, response),log); 
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;              
	}
	
}
