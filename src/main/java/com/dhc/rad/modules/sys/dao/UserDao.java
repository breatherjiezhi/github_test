/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.dhc.rad.modules.sys.entity.Role;
import com.dhc.rad.modules.sys.entity.UserTemplate;
import org.apache.ibatis.annotations.Param;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);
	
	/**
	 * 根据卡号查询用户
	 * @param cardID
	 * @return
	 */
	public User getByCardID(User user);
	/**
	 * 根据Q码查询用户
	 * @param qCode
	 * @return
	 */
	public User getByQCode(User user);

	public User getUserId(@Param("id")String id);
	public List<User> findUserByRoleName(@Param("roleName") String roleName);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

	public List<User> findUserByOffice(@Param("officeId") String officeId);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	public List<User>selectAll(@Param("getDataFlag") int getDataFlag, @Param("strFilter")String strFilter);

	public String getPwd(@Param("loginName") String strUserName);
	public List<User>selectUser(@Param("list")List<String> list,@Param("flag")String flag,@Param("officeId")String officeId);

	public List<User> findListByProjectOrg(User user);
	public void updateqCodeById(User user);

	public List<User> findUserByProjectOrg(List<String> list);

	public List<User> findForemanByProjectOrg(@Param("projectOrgId")String projectOrgId);

	/**
	* @Description: 根据用户id查询个人所属餐厅积分
	* @Param:  userId
	* @return:  List<Map<String,String>>
	* @Author: zhengXiang
	* @Date: 2021/4/20
	*/
	List<Map<String,String>> findPzMenuScoreById(@Param("userId") String userId);

	public Integer userRecharge(User user);

	public List<User> findYgList();
}
