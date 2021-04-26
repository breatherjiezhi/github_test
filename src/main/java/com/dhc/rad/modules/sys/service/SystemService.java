/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import com.dhc.rad.modules.sys.entity.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.security.Digests;
import com.dhc.rad.common.security.shiro.session.SessionDAO;
import com.dhc.rad.common.service.BaseService;
import com.dhc.rad.common.utils.CacheUtils;
import com.dhc.rad.common.utils.Encodes;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.sys.dao.MenuDao;
import com.dhc.rad.modules.sys.dao.NotifyDao;
import com.dhc.rad.modules.sys.dao.RoleDao;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.security.SystemAuthorizingRealm;
import com.dhc.rad.modules.sys.utils.LogUtils;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author DHC
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	private NotifyDao notifyDao;

	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private IdentityService identityService;

	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUserId(String id) {
		return userDao.getUserId(id);
	}
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	/**
	 * 根据卡号获取用户
	 * @param cardID
	 * @return
	 */
	public User getUserByCardID(String cardID) {
		return UserUtils.getByCardID(cardID);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		List<User> userList =userDao.findList(user);

		for (User user1:userList){
			Role role= new Role();
			role.setUser(user1);
			user1.setRoleList(roleDao.getNameList(role));
			user1.setRoleNames(user1.getRoleNameList());
		}
		// 执行分页查询
		page.setList(userList);
		return page;
	}
	public Page<Role> findRole(Page<Role> page, Role role) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		// 设置分页参数
		role.setPage(page);
		List<Role> roleList =roleDao.findList(role);
//		for (User user1:userList){
//			user1.setRoleNames(user1.getRoleNameList());
//		}
		// 执行分页查询
		page.setList(roleDao.findList2(role));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}


	/**
	 * 无分页查询员工列表
	 * @param
	 * @return
	 */
	public List<User> findYgList(){
		List<User> list = userDao.findYgList();
		return list;
	}



	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				//throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			//saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void updateqCodeById(User user) {
		user.preUpdate();
		userDao.updateqCodeById(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 同步到Activiti
		deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void batchDeleteUser(List<String> list) {
		for(int i = 0 ; i<list.size();i++){
			String id = list.get(i);
			deleteUser(new User(id));
		}
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(UserUtils.getSession().getHost());
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
//		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		UserUtils.clearCache();
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void saveRoleWithoutMenu(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}		
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
//		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		UserUtils.clearCache();
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void saveRoleMenu(Role role) {
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 清除用户角色缓存
//		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		UserUtils.clearCache();
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	public Office getCompany(String officeId) {
		return UserUtils.getCompany(officeId);
	}
	
	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	public List<Menu> findAllMenuTree(Boolean all){
		return UserUtils.getTreeMenuList(all);
	}
	// 获取手机菜单
	public List<Menu> getMobileMenuList(){
		return UserUtils.getMobileMenuList();
	}
	// 获取平板菜单
	public List<Menu> getPadMenuList(){
		return UserUtils.getPadMenuList();
	}
	
	
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		UserUtils.removeCache(UserUtils.CACHE_TREE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 * @throws IOException 
	 */
	public static boolean printKeyLoadMessage() throws IOException{
		Properties properties = new Properties();
	    // 使用ClassLoader加载properties配置文件生成对应的输入流
	    InputStream in = SystemService.class.getClassLoader().getResourceAsStream("rad.properties");
	    // 使用properties对象加载输入流
	    properties.load(in);
	    //获取key对应的value值
	    String productName = properties.getProperty("productName");
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用   " + productName);
		sb.append(" - Powered By CRRCDT\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
	//-- Notify Service --//	
	/**
	 * 发送通知
	 * @param title 通知标题
	 * @param content 通知内容
	 * @param isUrgent 是否紧急：1 紧急，0或null 非紧急
	 * @param senders 接受用户列表
	 * @param isSysterm 是否系统通知
	 * @param type 通知类型
	 */
	public boolean notify(String title,String content,boolean isUrgent,List<User> senders,boolean isSysterm,String type){
		List<Notify> notifyList = Lists.newArrayList();
		for(User user:senders){
			Notify notify = new Notify();
			notify.preInsert();
			notify.setTitle(title);
			notify.setContent(content);
			notify.setUrgentFlag(isUrgent?"1":"0");
			if(user.getLoginName()==null || user.getLoginName().equals("")){
				user=UserUtils.get(user.getId());
			}
			notify.setReceiver(user);
			notify.setReadFlag("0");
			if(!isSysterm){
				notify.setSenderId(UserUtils.getUser().getId());				
			}else{
				notify.setSenderId("");
			}
			notify.setType(type);
			notifyList.add(notify);
		}
		int result = notifyDao.insertAll(notifyList);		
		if(result==0){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取未读通知数
	 * @param readFlag
	 * @return
	 */
	public String unReadNotifyCount(String readFlag){
		//查询当前用户
		User user = UserUtils.getUser();
		if (user.getId() == null || user.getId().equals("")) {
			return "";
		}
		Notify notify = new Notify();
		notify.setReceiver(user);
		notify.setReadFlag(readFlag);		
		return String.valueOf(notifyDao.findCount(notify));
		
	}
		
	///////////////// Synchronized to the Activiti //////////////////
	
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if (isSynActivitiIndetity){
			isSynActivitiIndetity = false;
	        // 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0){
			 	Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
			 	while(roles.hasNext()) {
			 		Role role = roles.next();
			 		saveActivitiGroup(role);
			 	}
			}
		 	// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0){
			 	Iterator<User> users = userDao.findAllList(new User()).iterator();
			 	while(users.hasNext()) {
			 		saveActivitiUser(users.next());
			 	}
			}
		}
	}
	
	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String groupId = role.getEnname();
		
		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())){
			identityService.deleteGroup(role.getOldEnname());
		}
		
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);
		
		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList){
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList){
			String userId = e.getLoginName();//ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
			if (activitiUser == null){
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(role!=null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);
		
		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
	 		String groupId = role.getEnname();
	 		// 如果该用户组不存在，则创建一个
		 	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            if(group == null) {
	            group = identityService.newGroup(groupId);
	            group.setName(role.getName());
	            group.setType(role.getRoleType());
	            identityService.saveGroup(group);
            }
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(user!=null) {
			String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}

	// 手机菜单
	public List<Menu> getMobileRoleList() {
		return UserUtils.getMobileRoleList();
	}
	
	// 平板菜单
	public List<Menu> getPadRoleList() {
		return UserUtils.getPadRoleList();
	}
	
	///////////////// Synchronized to the Activiti end //////////////////

	public Role findrole(String userid){
		return roleDao.findrole(userid);
	}

	public List<Map<String,String>> findPzMenuScoreById(String userId) {
		return userDao.findPzMenuScoreById(userId);
	}



	@Transactional(readOnly = false)
	public Integer userRecharge(UserTemplate userTemplate) {
		User user = new User();
		user.setLoginName(userTemplate.getLoginName());
		user.setUserIntegral(BigDecimal.valueOf(userTemplate.getUserIntegral()));
		user.preUpdate();
		return userDao.userRecharge(user);
	}
}
