/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.dhc.rad.pbd.pbdproject.dao.PbdProjectDao;
//import com.dhc.rad.pbd.pbdproject.entity.PbdProject;
import com.dhc.rad.modules.wx.utils.RedissonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.dhc.rad.common.service.BaseService;
import com.dhc.rad.common.utils.CacheUtils;
import com.dhc.rad.common.utils.SpringContextHolder;
import com.dhc.rad.modules.sys.dao.AreaDao;
import com.dhc.rad.modules.sys.dao.MenuDao;
import com.dhc.rad.modules.sys.dao.OfficeDao;
import com.dhc.rad.modules.sys.dao.RoleDao;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.Area;
import com.dhc.rad.modules.sys.entity.Menu;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.entity.Role;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.google.common.collect.Lists;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 用户工具类
 *
 * @author DHC
 * @version 2013-12-05
 */
public class UserUtils {

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    //private static PbdProjectDao pbdProjectDao = SpringContextHolder.getBean(PbdProjectDao.class);

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_TREE_MENU_LIST = "treemenuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

    /**
     * 获取当前用户所分配的项目以及组织
     */
//	public static List<PbdProject> getUserProject(){
//		User user=getUser();
//		List<PbdProject>pbdProjectList =new ArrayList<>();
//		if (user!=null){
//			pbdProjectList=pbdProjectDao.findProjectByUser(user.getId());
//		}
//		return pbdProjectList;
//	}

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User getUserById(String id) {
        User user = new User();
        user = userDao.get(id);
        if (user == null) {
            return new User();
        }
        return user;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
//    public static User getByLoginName(String loginName) {
//        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
//        if (user == null) {
//            user = userDao.getByLoginName(new User(null, loginName));
//            if (user == null) {
//                return null;
//            }
//            user.setRoleList(roleDao.findList(new Role(user)));
//            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
//        }
//        return user;
//    }

    /**
     * 根据登录名获取用户(数据库获取)
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginNameDB(String loginName) {

            User user = userDao.getByLoginName(new User(null, loginName));
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
            return user;

    }


    /**
     * 根据卡号获取用户信息
     *
     * @param cardID
     * @return 取不到返回null
     */
    public static User getByCardID(String cardID) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + cardID);
        if (user == null) {
            User userInfo = new User();
            userInfo.setCardID(cardID);
            user = userDao.getByCardID(userInfo);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 根据卡号获取用户信息
     *
     * @param qCode
     * @return 取不到返回null
     */
    public static User getByQCode(String qCode) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + qCode);
        if (user == null) {
            User userInfo = new User();
            userInfo.setqCode(qCode);
            user = userDao.getByQCode(userInfo);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }


    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
        }
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                roleList = roleDao.findAllList(new Role());
            } else {
                Role role = new Role();
                role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
        if (menuList == null || menuList.isEmpty()) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    /**
     * 获取当前用户授权的手机菜单
     *
     * @return
     */
    public static List<Menu> getMobileMenuList() {
        List<Menu> menuList = Lists.newArrayList();
        if (menuList == null || menuList.isEmpty()) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findMobileMenuByUserId(m);
            }
        }
        return menuList;
    }


    /**
     * 获取当前用户授权的平板菜单
     *
     * @return
     */
    public static List<Menu> getPadMenuList() {
        List<Menu> menuList = Lists.newArrayList();
        if (menuList == null || menuList.isEmpty()) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findPadMenuByUserId(m);
            }
        }
        return menuList;
    }


    // 手机菜单
    public static List<Menu> getMobileRoleList() {
        List<Menu> roleList = Lists.newArrayList();
        if (roleList == null || roleList.isEmpty()) {
            User user = getUser();
            if (user.isAdmin()) {
                roleList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                roleList = menuDao.findMobileMenuByRole(m);
            }
        }
        return roleList;
    }


    // 平板菜单
    public static List<Menu> getPadRoleList() {
        List<Menu> roleList = Lists.newArrayList();
        if (roleList == null || roleList.isEmpty()) {
            User user = getUser();
            if (user.isAdmin()) {
                roleList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                roleList = menuDao.findPadMenuByRole(m);
            }
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单(子菜单包含在父菜单对象里)
     *
     * @return
     */
    public static List<Menu> getTreeMenuList(Boolean all) {
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>) getCache(CACHE_TREE_MENU_LIST);
        //if (menuList == null){
        List<Menu> result = new ArrayList<Menu>();
        menuList = getMenuList();
        Map<String, Menu> map = new HashMap<String, Menu>();
        for (Menu menu : menuList) {
            map.put(menu.getId(), menu);
            if (menu.getParentId().equals(Menu.getRootId())) {
                result.add(menu);
            }
        }
        for (Menu menu : menuList) {
            String pid = menu.getParentId();
            if (map.get(pid) != null && (menu.getIsShow().equals("1") || all)) {
                map.get(pid).getSubMenu().add(menu);
            }
        }
        menuList.clear();
        menuList = result;
        putCache(CACHE_TREE_MENU_LIST, menuList);
        //}
        return menuList;
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new Area());
            List<Area> result = new ArrayList<Area>();
            Map<String, Area> map = new HashMap<String, Area>();
            for (Area area : areaList) {
                map.put(area.getId(), area);
                if (area.getParentId().equals(Area.getRootId())) {
                    result.add(area);
                }
            }
            for (Area area : areaList) {
                String pid = area.getParentId();
                if (map.get(pid) != null) {
                    map.get(pid).getSubArea().add(area);
                }
            }
            areaList.clear();
            areaList = result;
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                officeList = convertOfficeTree(officeDao.findAllList(new Office()));
            } else {
                Office office = new Office();
                office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = convertOfficeTree(officeDao.findList(office));
            }
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = convertOfficeTree(officeDao.findAllList(new Office()));
        }
        return officeList;
    }

    private static List<Office> convertOfficeTree(List<Office> officeList) {
        List<Office> result = new ArrayList<Office>();
        Map<String, Office> map = new HashMap<String, Office>();
        for (Office office : officeList) {
            map.put(office.getId(), office);
            if (office.getParentId().equals(Office.getRootId())) {
                result.add(office);
            }
        }
        for (Office office : officeList) {
            String pid = office.getParentId();
            if (map.get(pid) != null) {
                map.get(pid).getSubOffice().add(office);
            }
        }
        officeList.clear();
        return result;
    }

    public static List<Office> getCompanys() {
        Office office = new Office();
        office.setType("1");
        List<Office> companys = officeDao.getCompanys(office);
        return companys;
    }

    public static Office getCompany(String officeId) {
        Office company = officeDao.get(new Office(officeId)).getCompany();
        return company;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

    /**
     * 判断用户是否具有权限
     *
     * @param roleName
     * @return
     */
    public static Boolean getRoleFlag(String roleName) {
        List<Role> roleList = getUser().getRoleList();
        for (Role role : roleList) {
            if (roleName.equals(role.getEnname())) {
                return true;
            }
        }
        return false;
    }


}
