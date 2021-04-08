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
import com.dhc.rad.common.utils.Collections3;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.Menu;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.entity.Role;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 角色Controller
 * @author DHC
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getRole(id);
		}else{
			return new Role();
		}
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"list", ""})
	public String list(Role role, Model model) {
		String qCode=UserUtils.getUser().getqCode();
		model.addAttribute("qCode",qCode);
		return "modules/sys/roleList";
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"searchPage"})
	@ResponseBody
	public Map<String,Object> searchPage(Role role, HttpServletRequest request, HttpServletResponse response) {
		Page<Role> page = systemService.findRole(new Page<Role>(request, response), role);
		//List<Role> list = systemService.findAllRole();
        Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("total", page.getTotalPage());
		returnMap.put("pageNo", page.getPageNo());
		returnMap.put("records", page.getCount());
		returnMap.put("rows", page.getList());
        return returnMap;
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		if (role.getOffice()==null){
			role.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("role", role);
		model.addAttribute("menuList", systemService.findAllMenu());
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/roleForm";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin()&&role.getSysData().equals(Global.YES)){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if (!beanValidator(model, role)){
			return form(role, model);
		}
		if (!"true".equals(checkName(role.getOldName(), role.getName()))){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 角色名已存在");
			return form(role, model);
		}
		if (!"true".equals(checkEnname(role.getOldEnname(), role.getEnname()))){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 英文名已存在");
			return form(role, model);
		}
		systemService.saveRole(role);
		addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(Role role, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
			return returnMap;
		}
		if(!UserUtils.getUser().isAdmin()&&role.getSysData().equals(Global.YES)){
			addMessageAjax(returnMap, "0", "越权操作，只有超级管理员才能修改此数据！");
			return returnMap;
		}
		
		if(role.getUseable()!=null && role.getUseable().equals("on")){
			role.setUseable("1");
		}else{
			role.setUseable("0");
		}
		
		if (!beanValidatorAjax(returnMap, role)){
			return returnMap;
		}
		Map<String,Object> checkNameResult =checkNameAjax(role.getName(),role.getId());
		if (checkNameResult.get("valid").equals("false")){
			addMessageAjax(returnMap, "0", "保存角色'" + role.getName() + "'失败, 角色名已存在");
			return returnMap;
		}
		Map<String,Object> checkEnnameResult =checkEnnameAjax(role.getName(),role.getId());
		if (checkEnnameResult.get("valid").equals("false")){
			addMessageAjax(returnMap, "0", "保存角色'" + role.getName() + "'失败, 英文名已存在");
			return returnMap;
		}
		systemService.saveRoleWithoutMenu(role);
		addMessageAjax(returnMap, "1",  "保存角色'" + role.getName() + "'成功");
		return returnMap;
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
//		if (Role.isAdmin(id)){
//			addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
////		}else if (UserUtils.getUser().getRoleIdList().contains(id)){
////			addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
//		}else{
			systemService.deleteRole(role);
			addMessage(redirectAttributes, "删除角色成功");
//		}
		return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "doDelete")
	@ResponseBody
	public  Map<String,Object> doDelete(String id, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = Maps.newHashMap();
		Role role = systemService.getRole(id);
		if(!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)){
			addMessageAjax(returnMap,"0","越权操作，只有超级管理员才能修改此数据！");
			return returnMap;
		}
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			systemService.deleteRole(role);
			addMessageAjax(returnMap,"1", "删除角色成功");
		}
		return returnMap;
	}
	
	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "assign")
	public String assign(Role role, Model model) {
		return "modules/sys/roleAssign";
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"assignList"})
	@ResponseBody
	public Map<String,Object> assignList(Role role,HttpServletRequest request, HttpServletResponse response) {

		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("rows", userList);
        return returnMap;
	}
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		List<Office> userList2 = officeService.findAll();
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
		model.addAttribute("officeList", userList2);
		return "modules/sys/selectUserToRole";
	}
	
	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(officeId));
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);			
		}
		return mapList;
	}
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+roleId;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		}else {
			if (user.getRoleList().size() <= 1){
				addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			}else{
				Boolean flag = systemService.outUserInRole(role, user);
				if (!flag) {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
				}else {
					addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}		
		}
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(idsArr[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name!=null && name.equals(oldName)) {
			return "true";
		} else if (name!=null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname!=null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname!=null && systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 验证角色名称是否被占用
	 * @param enname
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkNameAjax")
	public Map<String,Object> checkNameAjax(String name, String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		Role role = systemService.getRoleByName(name);
		if(role == null){
			map.put("valid",true);
		}else if(id != null && !id.equals("")){
			if(id.equals(role.getId())){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}
		}else{
			map.put("valid",false);
		}
		return map;
	}
	
	/**
	 * 验证英文名称是否被占用
	 * @param enname
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkEnnameAjax")
	public Map<String,Object> checkEnnameAjax(String enname, String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		Role role = systemService.getRoleByEnname(enname);
		if(role == null){
			map.put("valid",true);
		}else if(id != null && !id.equals("")){
			if(id.equals(role.getId())){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}
		}else{
			map.put("valid",false);
		}
		return map;
	}
	
	
	@RequiresPermissions("sys:role:edit")
	@ResponseBody
	@RequestMapping(value = "saveMenus")
	public Map<String,Object> saveMenus(String id,String menus, RedirectAttributes redirectAttributes) {
		
		Map<String,Object> returnMap = Maps.newHashMap();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
		}else{
			Role role = new Role(id);
			String[] menuArray = menus.split(",");
			if(menuArray.length!=0){
				for(String menu:menuArray){
					if(!menu.equals("")){
						role.getMenuList().add(new Menu(menu));
					}
				}
			}
			systemService.saveRoleMenu(role);
			addMessageAjax(returnMap,"1", "分配功能点成功");
		}
		return returnMap;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getMenutree")
	public Map<String, Object> getMenutree(String id, HttpServletResponse response) {
		Map<String,Object> result = Maps.newHashMap();
		Role role = systemService.getRole(id);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenuTree(true);
		List<String> selectList = role.getMenuIdList();
		result.put("data", convert2Tree(list,selectList));
		result.put("selectList", selectList);
		return result;
	}

	private List<Map<String, Object>> convert2Tree(List<Menu> list,List<String> selectList) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
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
			if(selectList.contains(e.getId())){
				Map<String,Boolean> checked = Maps.newHashMap();
				checked.put("checked", true);
				map.put("state",checked);
			}
			List<Map<String, Object>> nodes = convert2Tree(e.getSubMenu(),selectList);
			if(nodes.size()>0){
				map.put("nodes",nodes);
			}		
			mapList.add(map);
		}
		return mapList;
	}
	

}
