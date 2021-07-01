/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhc.rad.common.utils.ConstantUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.security.shiro.session.SessionDAO;
import com.dhc.rad.common.utils.CacheUtils;
import com.dhc.rad.common.utils.CookieUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.security.FormAuthenticationFilter;
import com.dhc.rad.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;

/**
 * 登录Controller
 *
 * @author DHC
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;


    /**
     * 管理登录
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {


        Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}

        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            CookieUtils.setCookie(response, "LOGINED", "false");
        }

        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {
            return "redirect:" + adminPath;
        }

        // 如果是手机登录，则返回JSON字符串
//		if (principal.isMobileLogin()){
//			Map<String,Object> map = Maps.newHashMap();
//			map.put("data", model);
//			map.put("resultStatus", "601");
//			return renderString(response, map);
//		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";

        return "modules/sys/sysLogin";
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {

        Principal principal = UserUtils.getPrincipal();

        // 如果已经登录，则跳转到管理首页
//		if(principal != null){
//			return "redirect:" + adminPath;
//		}

        boolean ajax = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_AJAX_PARAM);
        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            //判断是否是ajax请求
            if (ajax) {
                Map<String, Object> returnMap = Maps.newHashMap();
                addMessageAjax(returnMap, Global.ERROR, "非法请求.");
                return renderString(response, returnMap);
            }
            return "redirect:" + adminPath;
        }


        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String roleList = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_ROLE_LIST_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ROLE_LIST_PARAM, roleList);
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
//		model.addAttribute("message", message);

        if (logger.isDebugEnabled()) {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}",
                    sessionDAO.getActiveSessions(false).size(), message, exception);
        }

//		// 非授权异常，登录失败，验证码加1。
//		if (!UnauthorizedException.class.getName().equals(exception)){
//			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
//		}
//		
//		// 验证失败清空验证码
//		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

        // 如果是手机登录，则返回JSON字符串
        if (mobile) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("status", ConstantUtils.ResCode.LOGINFAIL);
            map.put("data", model);
            map.put("message", message);
            return renderString(response, map);
        }

        //判断是否是ajax请求
        if (ajax) {
            Map<String, Object> returnMap = Maps.newHashMap();
            addMessageAjax(returnMap, Global.ERROR, message);
            return renderString(response, returnMap);
        }
        return "modules/sys/sysLogin";
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();

//		// 登录成功后，验证码计算器清零
//		isValidateCodeLogin(principal.getLoginName(), false, true);

        if (logger.isDebugEnabled()) {
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils.isBlank(logined) || "false".equals(logined)) {
                CookieUtils.setCookie(response, "LOGINED", "true");
            } else if (StringUtils.equals(logined, "true")) {
                UserUtils.getSubject().logout();
                return "redirect:" + adminPath + "/login";
            }
        }
        String qCode = UserUtils.getUser().getqCode();
        model.addAttribute("qCode", qCode);
        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin()) {
            if (request.getParameter("login") != null) {
                //TODO:maliang 定义了一个map集合返回
                Map<String, Object> map = Maps.newHashMap();
                map.put("status", ConstantUtils.ResCode.LOGINSUCCESS);
                map.put("data", principal);
//				map.put("sessionid", principal.getSessionid());
                map.put("message", "登录成功");

                return renderString(response, map);
            }
            if (request.getParameter("index") != null) {
                return "modules/sys/sysIndex";
            }
            return "redirect:" + adminPath + "/login";
        }


        boolean ajax = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_AJAX_PARAM);
        //判断是否是ajax请求
        if (ajax) {
            Map<String, Object> returnMap = Maps.newHashMap();
            addMessageAjax(returnMap, Global.SUCCESS, "登录成功.");
            return renderString(response, returnMap);
        }


        return "modules/sys/sysIndex";
    }

    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }

    /**
     * 是否是验证码登录
     *
     * @param useruame 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }
}
