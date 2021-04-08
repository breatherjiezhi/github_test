/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.Menu;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 菜单Controller
 * @author DHC
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = systemService.findAllMenu();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
		return "modules/sys/menuList";
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
		return "modules/sys/menuForm";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save")
	public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能添加或修改数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
		if (!beanValidator(model, menu)){
			return form(menu, model);
		}
		systemService.saveMenu(menu);
		addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/menu/";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<String,Object>();
		if(!UserUtils.getUser().isAdmin()){
			addMessageAjax(returnMap,"0","越权操作，只有超级管理员才能添加或修改数据！");
		}
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}
		if(menu.getIsShow()!=null && menu.getIsShow().equals("on")){
			menu.setIsShow("1");
		}else{
			menu.setIsShow("0");
		}
		// 手机菜单
		if(menu.getIsMobile()!=null && menu.getIsMobile().equals("on")){
			menu.setIsMobile("1");
		}else{
			menu.setIsMobile("0");
		}
		// 平板菜单
		if(menu.getIsPad()!=null && menu.getIsPad().equals("on")){
			menu.setIsPad("1");
		}else{
			menu.setIsPad("0");
		}
		
		if (!beanValidatorAjax(returnMap, menu)){
			return returnMap;
		}
		systemService.saveMenu(menu);
		addMessageAjax(returnMap,"1","保存菜单'" + menu.getName() + "'成功");
		return returnMap;
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete")
	public String delete(Menu menu, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
			systemService.deleteMenu(menu);
			addMessage(redirectAttributes, "删除菜单成功");
//		}
		return "redirect:" + adminPath + "/sys/menu/";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "doDelete")
	@ResponseBody
	public Map<String,Object> doDelete(Menu menu, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		systemService.deleteMenu(menu);
		addMessageAjax(returnMap,"1","删除菜单成功");
		return returnMap;
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree() {
		return "modules/sys/menuTree";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "modules/sys/menuTreeselect";
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")//@Valid 
	@ResponseBody
	public Map<String,Object> updateSort(String name,String pk,int value,RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		Menu menu = new Menu(pk);
		menu.setSort(value);
		systemService.updateMenuSort(menu);
		addMessageAjax(returnMap,"1","保存菜单排序成功!'");
		return returnMap;
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}	
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getMenutree")
	public List<Map<String, Object>> getMenutree(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenuTree(false);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", "1");
		map.put("text","功能菜单");
		List<Map<String, Object>> nodes = convert2Tree(list);
		if(nodes.size()>0){
			map.put("nodes",nodes);
		}	
		mapList.add(map);
		return mapList;
	}

	private List<Map<String, Object>> convert2Tree(List<Menu> list) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if(e.getIsShow()!=null && e.getIsShow().equals("1")){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(e.getIcon() != null){
					if(e.getIcon().startsWith("glyphicon-")){
						map.put("icon", "glyphicon "+e.getIcon());
					}else if(e.getIcon().startsWith("fa-")){
						map.put("icon", "fa "+e.getIcon());
					}else{
						map.put("icon", e.getIcon());
					}
				}
				List<Map<String, Object>> nodes = convert2Tree(e.getSubMenu());
				if(nodes.size()>0){
					map.put("nodes",nodes);
				}		
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 函数功能说明 : 
	 * 作者: hj  
	 * 创建时间：2018-08-14
	 * 描述 : 手机菜单获取
	 * @参数： @param padFlag
	 * @参数： @param response
	 * @参数： @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "getMobileMenuTree")
	public Map<String, Object> getMobileMenutree(@RequestParam(required=false) String padFlag, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.getMobileMenuList();
		List<Menu> listrole = systemService.getMobileRoleList();
		Map<String,List<Menu>> MapMeun=new HashMap<>();
		Map<String, Object> resulMap = Maps.newHashMap();
		if (listrole==null||listrole.size()==0||listrole.equals("")) {
			resulMap.put("status", ConstantUtils.ResCode.PASSLIMITS);
			resulMap.put("message", ConstantUtils.ResCode.PASSLIMITSMSG);
		} else {
			String role = listrole.get(0).getEnname();
			for (Menu menu : list) {
				if (!menu.getName().equals("通知")) {
					String key =menu.getParent().getName();
					if (!MapMeun.containsKey(key)) {
						List<Menu> menus = new ArrayList<>();
						MapMeun.put(key, menus);
					}
					MapMeun.get(key).add(menu);
				}
			}
			List<Menu> menus=MapMeun.get("手持菜单");
			for (Menu menu:menus){
				List<Menu> menusList=MapMeun.get(menu.getName());
				Map<String, Object> menuMap1 = Maps.newHashMap();
				List<Map<String, Object>> mapList2 = Lists.newArrayList();
				for (Menu menu1:menusList){
					Map<String, Object> menuMap2 = Maps.newHashMap();
					menuMap2.put("href", menu1.getHref());
					menuMap2.put("name", menu1.getName());
					mapList2.add(menuMap2);
				}
				menuMap1.put("href", menu.getHref());
				menuMap1.put("name", menu.getName());
				menuMap1.put("childList",mapList2);
				menuMap1.put("english",menu.getSigns());
				mapList.add(menuMap1);
			}
			Map<String, Object> menuMap1 = Maps.newHashMap();
			menuMap1.put("href", "/sys/notify/notifyList");
			menuMap1.put("name", "通知");

			mapList.add(menuMap1);
			Map<String, Object> map = Maps.newHashMap();
			if (mapList.size() == 0) {
				map.put("roletype", role);
				map.put("menulist", mapList);
				resulMap.put("status", "200");
				resulMap.put("data", map);
				resulMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
			} else {
				map.put("roletype", role);
				map.put("menulist", mapList);
				resulMap.put("status", "200");
				resulMap.put("data", map);
				resulMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
			}

		}
		return resulMap;
	}
	
	/**
	 * 函数功能说明 : 
	 * 作者: hj  
	 * 创建时间：2018-08-14
	 * 描述 : 平板菜单获取
	 * @参数： @param extId
	 * @参数： @param response
	 * @参数： @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "getPadMenuTree")
	public Map<String, Object> getPadMenutree(@RequestParam(required=false) String mobileFlag, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();		
		List<Menu> list = systemService.getPadMenuList();
		List<Menu> listrole = systemService.getPadRoleList();
		
		Map<String, Object> resulMap = Maps.newHashMap();
		if (listrole==null||listrole.size()==0||listrole.equals("")) {
			resulMap.put("status", ConstantUtils.ResCode.PASSLIMITS);
			resulMap.put("message", ConstantUtils.ResCode.PASSLIMITSMSG);
		} else {
			String role = listrole.get(0).getEnname();
			for (Menu menu : list) {
				if (!menu.getName().equals("通知")) {
					Map<String, Object> menuMap = Maps.newHashMap();
					menuMap.put("href", menu.getHref());
					menuMap.put("name", menu.getName());
					mapList.add(menuMap);
				}
			}
			Map<String, Object> menuMap1 = Maps.newHashMap();
			menuMap1.put("href", "/sys/notify/notifyList");
			menuMap1.put("name", "通知");
			mapList.add(menuMap1);

			Map<String, Object> map = Maps.newHashMap();
			if (mapList.size() == 0) {
				map.put("roletype", role);
				map.put("menulist", mapList);
				resulMap.put("status", "200");
				resulMap.put("data", map);
				resulMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
			} else {
				map.put("roletype", role);
				map.put("menulist", mapList);
				resulMap.put("status", "200");
				resulMap.put("data", map);
				resulMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
			}

		}
		return resulMap;
	}
	
}
