package com.dhc.rad.modules.pzMenu.web;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.sys.entity.Role;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzMenu")
public class PzMenuController extends BaseController {
    //TODO:获取当前登录用户id与createById做判断 用于增删改查

    @Autowired
    private PzMenuService pzMenuService;

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public PzMenu get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return pzMenuService.get(id);
        }else {

            return new PzMenu();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response, Model model){
        List<PzMenu> pzMenuList = pzMenuService.findMenuList(pzMenu);
        model.addAttribute("pzMenuList", pzMenuList);
        return "modules/pzMenu/pzMenuList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response){
        Page<PzMenu> page = pzMenuService.searchPage(new Page<>(request, response), pzMenu);

        Map<String,Object> returnMap = new HashMap<>();

        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());

        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzMenu pzMenu,Model model){

        model.addAttribute("pzMenu", pzMenu);
        return "modules/pzMenu/pzMenuForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(PzMenu pzMenu,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        //修改：判断当前用户是否与创建用户一致
        String pzMenuId = pzMenu.getId();
        if (StringUtils.isNotBlank(pzMenuId)) {
            PzMenu menuById = pzMenuService.get(pzMenuId);
            String userId = UserUtils.getUser().getId();
            String createBy = menuById.getCreateBy().getId();
            if (menuById.getCreateBy()==null || !menuById.getCreateBy().getId().equals(userId)) {
                addMessageAjax(returnMap, "0", "当前用户无权限！！！");
                return returnMap;
            }
        }
            flag =  pzMenuService.saveOrUpdate(pzMenu);


        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> returnMap = new HashMap<>();
        //删除时判断当前用户是否与创建用户一致
        String userId = UserUtils.getUser().getId();
        List<String> idList = Arrays.asList(ids.split(","));
        for (String pzMenuId : idList) {
            PzMenu pzMenu = pzMenuService.get(pzMenuId);
            if (pzMenu==null || !pzMenu.getCreateBy().getId().equals(userId)) {
                addMessageAjax(returnMap, "0", "当前用户无权限！！！");
                return returnMap;
            }
        }

        Integer flag = pzMenuService.deleteByIds(idList);
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }
    @RequestMapping(value = "submitPzMenu", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> submitPzMenu(PzMenu pzMenu,HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> returnMap = new HashMap<>();

       Integer flag =  pzMenuService.submitPzMenu(pzMenu);

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "提交成功");
        } else {
            addMessageAjax(returnMap, "0", "提交失败");
        }

        return returnMap;
    }

    @RequestMapping(value = "updateStatus", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateMenuStatus(PzMenu pzMenu,HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> returnMap = new HashMap<>();
        //获取当前登录用户的id
        String userId = UserUtils.getUser().getId();
       //查询用户的角色英文名称
        Role findRole = systemService.findrole(userId);
        String enName = null;
        if (ObjectUtils.isNotEmpty(findRole)) {
            enName =  findRole.getEnname();
        }

        Integer menuStatusAgo = 0;

        //获取之前菜单状态
        if(StringUtils.isNotBlank(pzMenu.getId())){
            PzMenu menu = pzMenuService.get(pzMenu.getId());
            menuStatusAgo = menu.getMenuStatus();
        }
        //管理员审批菜单： 只有当菜单状态为待审核时，管理员才能操作
        if( menuStatusAgo==null || !"admin".equals(enName) || menuStatusAgo!= Global.MENU_STATUS_SUBMIT ){
            addMessageAjax(returnMap,"0","越权操作，只有管理员才能修改此数据！");
            return returnMap;
        }
        //供应商操作菜单：只有当菜单状态为非待审核（保存并修改  审核不通过）时，才能修改状态
        if(menuStatusAgo==null || !"gcs".equals(enName) || menuStatusAgo!=Global.MENU_STATUS_SAVEANDUPDATE || menuStatusAgo!=Global.MENU_STATUS_NOPASS){
            addMessageAjax(returnMap,"0","越权操作，只有供应商才能修改此数据！");
            return returnMap;
        }
        //普通用户无权操作菜单
        if ("staff".equals(enName)) {
            addMessageAjax(returnMap,"0","越权操作，普通用户无权操作菜单！");
            return returnMap;
        }

        //修改菜单状态
        Integer flag =  pzMenuService.updateMenuStatus(pzMenu);

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "状态修改成功");
        } else {
            addMessageAjax(returnMap, "0", "状态修改失败");
        }
        //返回
        return returnMap;
    }

    @RequestMapping(value="import",method = RequestMethod.GET)
    public String impTroubleExcels(HttpServletRequest request, HttpServletResponse response, Model model){
        return "modules/pzMenu/pzMenuFileInfo";
    }


}
