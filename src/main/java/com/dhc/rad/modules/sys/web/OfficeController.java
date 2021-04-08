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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.Area;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.utils.DictUtils;
import com.dhc.rad.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author DHC
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

//	@RequiresPermissions("sys:office:view")
//	@RequestMapping(value = {""})
//	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
//		return "modules/sys/officeList";
//	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list",""})
	public String list(Office office, Model model) {
		List<Office> list;
		if(office.getId()==null){
			list = officeService.findAll();
		}else{
			list = officeService.findList(office);			
		}
		List<Office> result = new ArrayList<Office>();
		getList(list,result);
		String qCode=UserUtils.getUser().getqCode();
		model.addAttribute("qCode",qCode);
        model.addAttribute("list", result);
		return "modules/sys/officeList";
	}
	
	private   void getList(List<Office> list, List<Office> result) {
		for(int i=0;i<list.size();i++){
			Office office = list.get(i);
			result.add(office);
			if(!office.getSubOffice().isEmpty()){
				getList(office.getSubOffice(), result);
			}
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}else{
			office.setParent(officeService.get(office.getParent().getId()));			
		}
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		List<Office> companys = officeService.getCompanys();
		String qCode=UserUtils.getUser().getqCode();
		model.addAttribute("qCode",qCode);
		model.addAttribute("office", office);
		model.addAttribute("companys", companys);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "doSave")
	@ResponseBody
	public Map<String,Object> doSave(Office office, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap, "0",  "演示模式，不允许操作！");
			return returnMap;
		}
		if (!beanValidatorAjax(returnMap, office)){
			return returnMap;
		}
		//@todo 所属公司未保存 
		officeService.save(office);
		
//		if(office.getChildDeptList()!=null){
//			Office childOffice = null;
//			for(String id : office.getChildDeptList()){
//				childOffice = new Office();
//				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
//				childOffice.setParent(office);
//				childOffice.setArea(office.getArea());
//				childOffice.setType("2");
//				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
//				childOffice.setUseable(Global.YES);
//				officeService.save(childOffice);
//			}
//		}
		addMessageAjax(returnMap,"1","保存机构'" + office.getName() + "'成功");
		return returnMap;
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
//		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "doDelete")
	@ResponseBody
	public Map<String,Object> doDelete(Office office, RedirectAttributes redirectAttributes) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(Global.isDemoMode()){
			addMessageAjax(returnMap,"0","演示模式，不允许操作！");
			return returnMap;
		}
		officeService.delete(office);
		addMessageAjax(returnMap,"1","删除机构成功");
		return returnMap;
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getOfficetree")
	public List<Map<String, Object>> getOfficetree(@RequestParam(required=false) String treetype, @RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll,  HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		Map<String,Object> map = new HashMap<String,Object>();
		if(treetype!=null && treetype.equals("1")){
			mapList = convert2Tree(list);
		}else{
			map.put("id", "0");
			map.put("text","根节点");
			List<Map<String, Object>> nodes = convert2Tree(list);
			if(nodes.size()>0){
				map.put("nodes",nodes);
			}	
			mapList.add(map);
		}
		return mapList;
	}
	
	private List<Map<String, Object>> convert2Tree(List<Office> list) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", e.getId());
			map.put("text", e.getName());
			List<Map<String, Object>> nodes = convert2Tree(e.getSubOffice());
			if(nodes.size()>0){
				map.put("nodes",nodes);
			}		
			mapList.add(map);
		}
		return mapList;
	}
	
}
