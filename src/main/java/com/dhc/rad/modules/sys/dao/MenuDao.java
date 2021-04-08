/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.dao;

import java.util.List;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author DHC
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	// 手机菜单
	public List<Menu> findMobileMenuByUserId(Menu menu);
	// 平板菜单
	public List<Menu> findPadMenuByUserId(Menu menu);

	// 手机菜单权限
	public List<Menu> findMobileMenuByRole(Menu m);
	
	// 平板菜单权限
	public List<Menu> findPadMenuByRole(Menu m);
	
}
