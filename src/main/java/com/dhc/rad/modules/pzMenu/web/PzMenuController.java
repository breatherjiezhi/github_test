package com.dhc.rad.modules.pzMenu.web;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modbus.entity.func.Util;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import com.dhc.rad.modules.pzMenuContent.service.PzMenuContentService;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.sys.entity.Role;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.sun.xml.internal.bind.v2.TODO;
import jodd.time.TimeUtil;
import org.activiti.explorer.util.time.timeunit.WeekTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

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
    private PzMenuContentService pzMenuContentService;

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public PzMenu get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return pzMenuService.getByid(id);
        } else {

            return new PzMenu();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/pzMenu/pzMenuList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response) {
        String officeId = UserUtils.getUser().getOffice().getId();

        if(!(UserUtils.getRoleFlag("admin") || UserUtils.getRoleFlag("admins"))){
            pzMenu.setRestaurantId(officeId);
        }

        Page<PzMenu> page = pzMenuService.searchPage(new Page<>(request, response), pzMenu);

        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());

        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzMenu pzMenu, Model model) {
        List<PzMenuContent> pzMenuContentList = new ArrayList<PzMenuContent>();

        if(pzMenu!=null&&StringUtils.isNotBlank(pzMenu.getId())){
            pzMenuContentList =  pzMenuContentService.findAllListByMenuId(pzMenu.getId());
        }else{
            if(pzMenuContentList.size()==0){
               List<String> list =  TimeUtils.getNextWeekEatDate();
                for (String temp : list) {
                    PzMenuContent pmc = new PzMenuContent();
                    pmc.setEatDate(temp);
                    pmc.setEatWeek(TimeUtils.getWeekDay(temp));
                    pzMenuContentList.add(pmc);
                }

            }
        }
        pzMenu.setPzMenuContentList(pzMenuContentList);
        model.addAttribute("httpUrl", Util.getImgUrl());
        model.addAttribute("pzMenu", pzMenu);
        return "modules/pzMenu/pzMenuForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String, Object> doSave(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = 0;
        User user = UserUtils.getUser();

        if(StringUtils.isEmpty(pzMenu.getMenuName())){
            addMessageAjax(returnMap, "0", pzMenu.getMenuName()+"不能为空！！！");
            return returnMap;
        }

        //修改：判断当前用户是否与创建用户一致
        String pzMenuId = pzMenu.getId();
        if (StringUtils.isNotBlank(pzMenuId)) {
            PzMenu menuById = pzMenuService.get(pzMenuId);
            String userId = user.getId();
            String createBy = menuById.getCreateBy().getId();
            if (menuById.getCreateBy() == null || !createBy.equals(userId)) {
                addMessageAjax(returnMap, "0", "当前用户无权限！！！");
                return returnMap;
            }
        }

        List<PzMenuContent> list =  pzMenu.getPzMenuContentList();
        String eatDate = "";
        for (PzMenuContent pzMenuContent : list) {
            if("1".equals(pzMenuContent.getMenuLimited()) &&(pzMenuContent.getMenuCount()==null || pzMenuContent.getMenuCount()==0)){
                addMessageAjax(returnMap, "0", "请修改[ "+pzMenuContent.getMenuDetail()+" ]套餐限量份数！！！");
                return returnMap;
            }
            eatDate+=pzMenuContent.getEatDate()+",";
        }
        //新增：防止同一个菜单重复提交
        if(StringUtils.isEmpty(pzMenuId)){
            PzMenu condition = new PzMenu();
            condition.setMenuName(pzMenu.getMenuName());
            condition.setEatDate(eatDate);
            condition.setRestaurantId(user.getOffice().getId());
            List<PzMenu> menuList = pzMenuService.findList(condition);
            if(menuList.size() >0){
                addMessageAjax(returnMap, "0", "下周菜单已添加");
                return returnMap;
            }
        }


        pzMenu.setEatDate(eatDate);
        pzMenu.setRestaurantId(user.getOffice().getId());
        flag = pzMenuService.saveOrUpdate(pzMenu,list);


        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        //删除时判断当前用户是否与创建用户一致
        String userId = UserUtils.getUser().getId();
        List<String> idList = Arrays.asList(ids.split(","));
        for (String pzMenuId : idList) {
            PzMenu pzMenu = pzMenuService.get(pzMenuId);
            if (pzMenu == null || !pzMenu.getCreateBy().getId().equals(userId)) {
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

    @RequestMapping(value = "submitPzMenu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitPzMenu(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<>();

        Integer flag = pzMenuService.submitPzMenu(pzMenu);

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "提交成功");
        } else {
            addMessageAjax(returnMap, "0", "提交失败");
        }

        return returnMap;
    }

    /**
     * @Description: 审核菜单是否合格
     * @Param: pzMenu
     * @return: Map<String,Object>
     * @Date: 2021/4/21
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateMenuStatus(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<>();
        if(!UserUtils.getRoleFlag("admins") && !UserUtils.getRoleFlag("admin") && !UserUtils.getRoleFlag("gcs")){
            addMessageAjax(returnMap, "0", "无权修改此数据！");
            return returnMap;
        }
        PzMenu newPzMenu = new PzMenu();
        //获取当前登录用户的id
        Integer menuStatusAgo = null;
        PzMenu menu = null;
        //获取之前菜单状态
        if (StringUtils.isNotBlank(pzMenu.getId())) {
            menu = pzMenuService.get(pzMenu.getId());
            menuStatusAgo = menu.getMenuStatus();
        } else {
            addMessageAjax(returnMap, "0", "参数错误！");
            return returnMap;
        }
        User user = UserUtils.getUser();
        if (!user.getId().equals(pzMenu.getCreateBy().getId())) {
            //管理员审批菜单： 只有当菜单状态为待审核时，管理员才能操作
            if(menuStatusAgo!=null &&  menuStatusAgo == Global.MENU_STATUS_SUBMIT){
                if( !UserUtils.getRoleFlag("admins") && !UserUtils.getRoleFlag("admin")){
                    addMessageAjax(returnMap, "0", "非管理员无权修改此数据！");
                    return returnMap;
                }
            }

            //供应商操作菜单：只有当菜单状态为非待审核（保存并修改  审核不通过）时，才能修改状态
            if(menuStatusAgo != null && (menuStatusAgo == Global.MENU_STATUS_SAVEANDUPDATE || menuStatusAgo == Global.MENU_STATUS_NOPASS)){
                if(!UserUtils.getRoleFlag("gcs")){
                    addMessageAjax(returnMap, "0", "非供应商无权修改此数据！");
                    return returnMap;
                }
            }

        }

        newPzMenu.setId(pzMenu.getId());
        newPzMenu.setExamineInfo(pzMenu.getExamineInfo());
        newPzMenu.setMenuStatus(pzMenu.getMenuStatus());
        //修改菜单状态
        Integer flag = pzMenuService.updateMenuStatus(newPzMenu);

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "状态修改成功");
        } else {
            addMessageAjax(returnMap, "0", "状态修改失败");
        }
        //返回
        return returnMap;
    }

    @RequestMapping(value = "import", method = RequestMethod.GET)
    public String impTroubleExcels(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/pzMenu/pzMenuFileInfo";
    }

    /**
     * 查询菜单状态为未审核的菜单信息
     *
     * @param pzMenu
     * @param request
     * @param response
     * @return Map<String   ,   Object>
     */
    @RequestMapping(value = {"findMenuListByNoExamine"})
    @ResponseBody
    public Map<String, Object> findListByNoExamine(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> returnMap = new HashMap<>();
        //只有管理员才有权限操作
        //获取当前登录用户
        String userId = UserUtils.getUser().getId();
        //查询用户的角色英文名称
        if (!UserUtils.getRoleFlag("admin") && !UserUtils.getRoleFlag("admins")) {
            addMessageAjax(returnMap, "0", "越权操作，只有管理员才能查询此数据！");
            return returnMap;
        }
        //查询未审核的菜单数据信息
        Page<PzMenu> page = pzMenuService.findListByNoExamine(new Page<>(request, response), pzMenu);

        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());


        return returnMap;
    }

    /**
     * 查询菜单状态为未审核的菜单信息，然后跳转到submitList.jsp
     *
     * @param pzMenu
     * @param request
     * @param response
     * @param model
     * @return String
     */
    @RequestMapping(value = {"submitList"})
    public String submitList(PzMenu pzMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PzMenu> page = pzMenuService.findListByNoExamine(new Page<>(request, response), pzMenu);
        List<PzMenu> submitList = page.getList();
        model.addAttribute("submitList", submitList);
        return "modules/pzMenu/pzMenuExamineList";
    }

    /**
     * 审核菜单状态为未审核信息的表单
     *
     * @param pzMenu
     * @param model
     * @return String
     */
    @RequestMapping(value = "noExamineForm")
    public String noExamineForm(PzMenu pzMenu, Model model) {
        model.addAttribute("httpUrl", Util.getImgUrl());
        model.addAttribute("pzMenuSubmit", pzMenu);
        return "modules/pzMenu/pzMenuNoExamineForm";
    }


}
