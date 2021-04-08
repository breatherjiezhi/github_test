/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.sys.dao.NotifyDao;
import com.dhc.rad.modules.sys.entity.Notify;



/**
 * 通知Service
 * @author lijunjie
 * @version 2015-11-21
 */

@Service
@Transactional(readOnly = true)
public class NotifyService extends CrudService<NotifyDao, Notify>{
	@Autowired
	private NotifyDao notifyDao;
	
	public List<Map<String, Object>> getNotifyListByUserId(String userId) {
		return notifyDao.getNotifyListByUserId(userId);
	}
	public int readNotify(Notify notify) {
		return notifyDao.readNotify(notify);
	}
	/**
	 * 获取我的未读通知数目
	 * @param notify
	 * @return
	 */
	public Long findCount(Notify notify) {
		return dao.findCount(notify);
	}
	public List<Notify> showFindList(Notify notify) {
		// TODO Auto-generated method stub
		return notifyDao.showFindList(notify);
	}


    public void doSetRead(List<String> list) {
		notifyDao.doSetRead(list);
    }

	public void doSetAllRead() {
		notifyDao.doSetAllRead();
	}
}
