/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhc.rad.common.service.TreeService;
import com.dhc.rad.modules.sys.dao.OfficeDao;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author DHC
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private OfficeDao OfficeDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_ALL_LIST);
	}

	public List<Office> getCompanys() {
		return UserUtils.getCompanys();
	}


	public List<Office> findRestaurantOffice(){
		return OfficeDao.findRestaurantOffice();

	}


	public List<Office> findServiceUnit(){
		return OfficeDao.findServiceUnit();
	}
	
}
