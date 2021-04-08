/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.sys.entity.Notify;


/**
 * 通知DAO接口
 * @author lijunjie
 * @version 2015-11-21
 */


@MyBatisDao
public interface NotifyDao extends CrudDao<Notify>{

	public List<Map<String,Object>> getNotifyListByUserId(String userId);

	public int readNotify(Notify notify);

	public int insertAll(List<Notify> notifyList);

	public Long findCount(Notify notify);
	public List<Notify> showFindList(Notify notify);

	public void doSetRead(List<String> list);

	public void doSetAllRead();

//	public Map<String, Object> getCurrentamount(Map<String, String> mapWhere);
//
//	public void save(Map<String, Object> map);
//
//	public void update(Map<String, Object> map1);
//
//	public List<Map<String, Object>> getNinnum(Map<String, Object> map);



}
