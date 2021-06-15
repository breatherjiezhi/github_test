package com.dhc.rad.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

//import com.dhc.rad.pbd.pbdproject.entity.PbdProject;
import com.dhc.rad.modules.sys.entity.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dhc.rad.common.beanvalidator.BeanValidators;
import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.DateUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.excel.ExportExcel;
import com.dhc.rad.common.utils.excel.ImportExcel;
import com.dhc.rad.common.utils.jqgridSearch.JqGridHandler;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.common.web.Servlets;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 用户Controller
 *
 * @author DHC
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getUser(id);
        } else {
            return new User();
        }
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"index"})
    public String index(User user, Model model) {
        String qCode = UserUtils.getUser().getqCode();
        model.addAttribute("qCode", qCode);
        return "modules/sys/userIndex";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        return "modules/sys/userList";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(User user, HttpServletRequest request, HttpServletResponse response) {
        String where = new JqGridHandler(request).getWheres(null, true);
        System.out.println("sql:" + where);
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        //String result = JsonMapper.toJsonString(page.getList());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequiresPermissions("sys:user:save")
    @RequestMapping(value = "form")
    public String form(User user, HttpServletRequest request, Model model) {
        //update by maliang 页面请求先执行上面的get方法，返回user对象，此时user内的部门从数据库中查询
        //是正确的，放入缓存中，随后页面传来的office.id和office.name覆盖user对象中的相应属性，缓存中的user也被改变，导致此bug。
        //这里有安全隐患，页面传入的如果和对象匹配，可随意修改缓存中的值。
        //解决方法，页面值改变名称
        String officeId = request.getParameter("pageOfficeId");
        String officeName = request.getParameter("pageOfficeName");
        Office office = new Office(officeId);
        office.setName(officeName);
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(new Office());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(office);
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", systemService.findAllRole());
        return "modules/sys/userForm";
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        user.setCompany(new Office(request.getParameter("company.id")));
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)) {
            return form(user, request, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, request, model);
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
            //UserUtils.getCacheMap().clear();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 上传用户头像
     *
     * @author lijunjie
     * @version 2016-3-15
     */
    //@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "saveAvatar")
    @ResponseBody
    public Map<String, Object> saveAvatar(@RequestParam MultipartFile avatar, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        User user = UserUtils.getUser();
        if (avatar.isEmpty()) {
            returnMap.put("status", "0");
            returnMap.put("message", "请选择头像上传");
            return returnMap;
        }
        String imgName = avatar.getOriginalFilename();
        String suffix = imgName.substring(imgName.lastIndexOf(".") + 1, imgName.length());
        //todo 可以验证图片格式
        String name = user.getId() + "_large";
        String urlString = "/uploads/userAvatar/";

        //web端访问路径
        String filePath = Servlets.getRequest().getContextPath() +
                urlString + name + "." + suffix;
        //本地保存路径
        String fileLarge = Global.getUserfilesBaseDir() + urlString + user.getId() + "_large" + "." + suffix;
        String fileMiddle = Global.getUserfilesBaseDir() + urlString + user.getId() + "_middle" + "." + suffix;
        String fileSmall = Global.getUserfilesBaseDir() + urlString + user.getId() + "_small" + "." + suffix;
        String fileOr = Global.getUserfilesBaseDir() + urlString + user.getId() + "." + suffix;
        //创建目录
        File oldFile = new File(fileOr);
        if (!oldFile.exists()) {
            oldFile.mkdirs();
        }
        //保存原图片
        try {
            avatar.transferTo(oldFile);
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("status", "0");
            returnMap.put("message", "存储用户'" + user.getLoginName() + "'头像失败,请重试");
            return returnMap;
        }
        try {
        	/*PhotoUtils.zoomImage(fileOr, fileLarge, 200, 200);
        	PhotoUtils.zoomImage(fileOr, fileMiddle, 100, 100);
        	PhotoUtils.zoomImage(fileOr, fileSmall, 50, 50);
*/

            /*
             * size(width,height) 若图片横比200小，高比300小，不变
             * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
             * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
             */
            Thumbnails.of(fileOr).size(200, 200).toFile(fileLarge);
            Thumbnails.of(fileOr).size(100, 100).toFile(fileMiddle);
            Thumbnails.of(fileOr).size(50, 50).toFile(fileSmall);


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            returnMap.put("status", "0");
            returnMap.put("message", "存储用户'" + user.getLoginName() + "'头像失败,请重试");
            return returnMap;
        }
        //此处上传只存储图片的格式，具体的图片由     uploads/userAvatar/user.id_large+user.photo
        user.setPhoto("." + suffix);
        try {
            systemService.updateUserInfo(user);
        } catch (Exception e) {
            // TODO: handle exception
            returnMap.put("status", "0");
            returnMap.put("message", "保存用户'" + user.getLoginName() + "'头像失败");
        }
        returnMap.put("status", "1");
        returnMap.put("url", filePath);
        return returnMap;
    }


    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "doSave")
    @ResponseBody
    public Map<String, Object> doSave(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (Global.isDemoMode()) {
            addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
            return returnMap;
        }

        if (user.getLoginFlag() != null && user.getLoginFlag().equals("on")) {
            user.setLoginFlag("1");
        } else {
            user.setLoginFlag("0");
        }
        // 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
        String officeId = request.getParameter("office.id");
        user.setOffice(new Office(officeId));
        Office company = systemService.getCompany(officeId);
        user.setCompany(company);
        user.setCardID(user.getNo());
        // 如果新密码为空，则不更换密码
        if (!StringUtils.isNotBlank(user.getNewPassword()) && (user.getId() == null || user.getId().equals(""))) {
            user.setNewPassword("123456");
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidatorAjax(returnMap, user)) {
            return returnMap;
        }
        Map<String, Object> checkResult = checkLoginNameAjax(user.getLoginName(), user.getId());
        if (checkResult.get("valid").equals("false")) {
            addMessageAjax(returnMap, "0", "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return returnMap;
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        List<String> roleIdList = user.getRoleIdList();
        for (Role r : systemService.findAllRole()) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        if (roleList.size() == 0 && user.getLoginFlag().equals("1")) {
            addMessageAjax(returnMap, "0", "保存用户'" + user.getLoginName() + "'失败，允许登录用户没有分配角色");
            return returnMap;
        }
        user.setRoleList(roleList);
        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
            UserUtils.clearCache();
        }
        addMessageAjax(returnMap, "1", "保存用户'" + user.getLoginName() + "'成功");
        return returnMap;
    }


    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, Object> delete(User user, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (Global.isDemoMode()) {
            addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
            return returnMap;
        }
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessageAjax(returnMap, "0", "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessageAjax(returnMap, "0", "删除用户失败, 不允许删除超级管理员用户");
        } else {
            systemService.deleteUser(user);
            addMessageAjax(returnMap, "1", "删除用户成功");
        }
        return returnMap;
    }

    /**
     * 函数功能说明 :批量删除用户
     *
     * @throws
     * @参数： @param ids
     * @参数： @param redirectAttributes
     * @参数： @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public Map<String, Object> batchDelete(String[] ids, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (Global.isDemoMode()) {
            addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
            return returnMap;
        }
        List<String> list = Arrays.asList(ids);
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i);
            if (UserUtils.getUser().getId().equals(id)) {
                addMessageAjax(returnMap, "0", "删除用户失败, 不允许删除当前用户");
                return returnMap;
            } else if (User.isAdmin(id)) {
                addMessageAjax(returnMap, "0", "删除用户失败, 不允许删除超级管理员用户");
                return returnMap;
            }
        }
        systemService.batchDeleteUser(list);
        addMessageAjax(returnMap, "1", "删除用户成功");
        return returnMap;
    }

    /**
     * 函数功能说明 :重置用户密码
     *
     * @throws
     * @参数： @param id
     * @参数： @param redirectAttributes
     * @参数： @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "resetPwd")
    @ResponseBody
    public Map<String, Object> resetPwd(String id, String loginName, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (Global.isDemoMode()) {
            addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
            return returnMap;
        }
        if (!UserUtils.getUser().isAdmin()) {
            addMessageAjax(returnMap, "0", "非超级管理员用户不可以重置用户密码！");
            return returnMap;
        }
        systemService.updatePasswordById(id, loginName, "123456");
        addMessageAjax(returnMap, "1", "重置密码成功");
        return returnMap;
    }



    /**
     * 函数功能说明 :重置用户密码
     *
     * @throws
     * @参数： @param id
     * @参数： @param redirectAttributes
     * @参数： @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "resetAllPwd")
    @ResponseBody
    public Map<String, Object> resetAllPwd(String id, String loginName, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        if (Global.isDemoMode()) {
            addMessageAjax(returnMap, "0", "演示模式，不允许操作！");
            return returnMap;
        }
        if (!UserUtils.getUser().isAdmin()) {
            addMessageAjax(returnMap, "0", "非超级管理员用户不可以重置用户密码！");
            return returnMap;
        }
        systemService.updatePasswordCreateBy();
        addMessageAjax(returnMap, "1", "重置密码成功");
        return returnMap;
    }

    /**
     * 导出用户数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
            new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<User> list = ei.getDataList(User.class);
            for (User user : list) {
                try {
                    if ("true".equals(checkLoginName("", user.getLoginName()))) {
                        user.setPassword(SystemService.entryptPassword("123456"));
                        BeanValidators.validateWithException(validator, user);
                        systemService.saveUser(user);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "用户数据导入模板.xlsx";
            List<User> list = Lists.newArrayList();
            list.add(UserUtils.getUser());
            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 验证登录名是否有效
     *
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 验证登录名是否被占用
     *
     * @param loginName
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginNameAjax")
    public Map<String, Object> checkLoginNameAjax(String loginName, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = systemService.getUserByLoginName(loginName);
        if (user == null) {
            map.put("valid", true);
        } else if (id != null && !id.equals("")) {
            if (id.equals(user.getId())) {
                map.put("valid", true);
            } else {
                map.put("valid", false);
            }
        } else {
            map.put("valid", false);
        }
        return map;
    }

    /**
     * 首页
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "homePage")
    public String homePage(User user, HttpServletResponse response, Model model) {
        User currentUser = systemService.getUserId(UserUtils.getUser().getId());
        currentUser.setRoleNames(currentUser.getRoleNameList());
        String unReadNotifys = systemService.unReadNotifyCount(Notify.READ_FLAG_NO);
//		List<PbdProject> pbdProjectList=UserUtils.getUserProject();
//
//        model.addAttribute("pbdProjectList", pbdProjectList);

        //根据用户id查询用户信息
        List<Map<String, String>> mapList = systemService.findPzMenuScoreById(currentUser.getId());

        model.addAttribute("mapList", mapList);
        model.addAttribute("unReadNotifys", unReadNotifys);
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new Global());
        return "modules/sys/homePage";
    }

    /**
     * 用户信息显示
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getName())){
//			if(Global.isDemoMode()){
//				model.addAttribute("message", "演示模式，不允许操作！");
//				return "modules/sys/userInfo";
//			}
//			currentUser.setEmail(user.getEmail());
//			currentUser.setPhone(user.getPhone());
//			currentUser.setMobile(user.getMobile());
//			currentUser.setRemarks(user.getRemarks());
//			currentUser.setPhoto(user.getPhoto());
//			systemService.updateUserInfo(currentUser);
//			model.addAttribute("message", "保存用户信息成功");
//		}
        String unReadNotifys = systemService.unReadNotifyCount(Notify.READ_FLAG_NO);
        model.addAttribute("unReadNotifys", unReadNotifys);
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new Global());
        return "modules/sys/userInfo";
    }

    /**
     * 用户信息保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "saveInfo")
    @ResponseBody
    public void saveInfo(User user, HttpServletResponse response, Model model) {
        User currentUser = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getName())) {

            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setMobile(user.getMobile());
            currentUser.setRemarks(user.getRemarks());
            currentUser.setPhoto(user.getPhoto());
            systemService.updateUserInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        model.addAttribute("user", currentUser);
//		model.addAttribute("Global", new Global());
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("user", currentUser);
        returnMap.put("message", "保存用户信息成功");
        ResMessage(response, "1", returnMap);
//		return returnMap;
    }

    public void ResMessage(HttpServletResponse response, String idStr, Map<String, Object> valueStr) {
        //{"result_code":"?","result":{"?"}}
        StringBuffer resStrBuff = new StringBuffer();

        resStrBuff.append(valueStr);


        PrintWriter out = null;


        try {
            out = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (out != null) {
            out.println(resStrBuff.toString());
            out.flush();
            out.close();
        }
    }


    /**
     * 修改个人用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if (Global.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userModifyPwd";
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                model.addAttribute("message", "修改密码成功");
            } else {
                model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd";
    }

    /**
     * 修改个人用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "TomodifyPwd")
    public String TomodifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        model.addAttribute("user", user);
        return "modules/sys/userModifyPwd1";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "doModifyPwd")
    @ResponseBody
    public Map<String, Object> doModifyPwd(String oldPassword, String newPassword, Model model) {
        User user = UserUtils.getUser();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        out:
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {

            if (Global.isDemoMode()) {
                //model.addAttribute("message", "演示模式，不允许操作！");
                returnMap.put("message", "演示模式，不允许操作！");
                break out;
            }
            if (SystemService.validatePassword(oldPassword, user.getPassword())) {
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                returnMap.put("message", "修改密码成功");
                returnMap.put("status", "success");
                break out;
//				model.addAttribute("message", "修改密码成功");
            } else {
                returnMap.put("message", "修改密码失败，旧密码错误");
                returnMap.put("status", "failure");
                break out;
//				model.addAttribute("message", "修改密码失败，旧密码错误");
            }
        }

        return returnMap;
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId);
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_" + e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    @RequestMapping(value = "updateLanguage")
    @ResponseBody
    public Map<String, Object> updateLanguage(String qCode, RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        User user = UserUtils.getUser();
        user.setqCode(qCode);
        try {
            systemService.updateqCodeById(user);
            addMessageAjax(returnMap, "1", "语言更新成功");
        } catch (Exception e) {
            addMessageAjax(returnMap, "0", "语言更新失败");
        }

        return returnMap;
    }


    //	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}


    /**
     * 下载excel模板
     */
    @RequestMapping("downloadexcel")
    public String downloadPermMatrix(HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

        try {
            String fileName = "批量充值模版.xlsx";
            List<UserTemplate> list = Lists.newArrayList();
            List<User> userList = systemService.findYgList();
            if (userList.size() > 0) {
                for (User user : userList) {
                    UserTemplate u = new UserTemplate();
                    u.setLoginName(user.getLoginName());
                    u.setUserIntegral(0);
                    list.add(u);
                }
            } else {
                list.add(new UserTemplate());
            }

            new ExportExcel("批量充值模版", UserTemplate.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sys/user/list?repage";
    }

    /**
     * 批量充值
     */
    @RequestMapping(value = "importRecharge", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importRecharge(MultipartFile file, RedirectAttributes redirectAttributes) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer successResult = 0;
        Integer failResult = 0;
        try {
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<UserTemplate> userTemplateList = ei.getDataList(UserTemplate.class);
            for (UserTemplate userTemplate : userTemplateList) {
                int resultFlag = systemService.userRecharge(userTemplate);
                if (resultFlag > 0) {
                    successResult++;
                } else {
                    failResult++;
                }
            }
            if (userTemplateList.size() == successResult) {
                addMessageAjax(resultMap, "1", "全部充值成功，" + "共" + successResult + "条数据");
            } else {
                addMessageAjax(resultMap, "1", successResult + "条充值成功，" + "共" + failResult + "条数据充值失败");
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            addMessageAjax(resultMap, "0", "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
            addMessageAjax(resultMap, "0", "导入失败");
        }

        return resultMap;
    }

    /**
     * 个人充值
     */
    @RequestMapping(value = "personalRecharge", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> personalRecharge(User user, RedirectAttributes redirectAttributes) {
        Map<String,Object> returnMap = new HashMap<>();
        if(StringUtils.isEmpty(user.getLoginName())){
            addMessageAjax(returnMap, "0", "用户登录名不能为空");
            return returnMap;
        }
       Integer flag =  systemService.personalRecharge(user);
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "充值成功");
        } else {
            addMessageAjax(returnMap, "0", "充值失败");
        }
        return returnMap;
    }


}
