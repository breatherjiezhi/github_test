/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;

/**
 * APP用户退出Controller
 * @author lijunjie
 * @version 2015-12-14
 */
@Controller
public class LogoutController extends BaseController{
	
	/**
	 * 手机端退出功能
	 */
	@ResponseBody
	@RequestMapping(value = "${adminPath}/mobileLogout")
	public Map<String,Object> logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> resultMap = Maps.newHashMap();
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			resultMap.put("status", ConstantUtils.ResCode.USERIDISWORNG);
			resultMap.put("message", "无权操作");
			return resultMap;
		}
//		System.out.println("Yunqi-Testing："+"我的测试点+退出");
//		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
//		System.out.println("mobile："+mobile);
		try {
			UserUtils.getSubject().logout();
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("status", ConstantUtils.ResCode.SERVERERROR);
			resultMap.put("message", e.getMessage());
			return resultMap;
		}
		resultMap.put("status", "200");
		resultMap.put("message", "退出成功");
		return resultMap;
		
	}

	
}
