/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.kaoqin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.kaoqin.entity.AomKaoqin;
import com.dhc.rad.modules.kaoqin.dao.AomKaoqinDao;

/**
 * 考勤信息Service
 * @author fangzr
 * @version 2015-07-15
 */
@Service
@Transactional(readOnly = true)
public class AomKaoqinService extends CrudService<AomKaoqinDao, AomKaoqin> {

	public AomKaoqin get(String id) {
		return super.get(id);
	}
	
	public List<AomKaoqin> findList(AomKaoqin aomKaoqin) {
		return super.findList(aomKaoqin);
	}
	
	public Page<AomKaoqin> findPage(Page<AomKaoqin> page, AomKaoqin aomKaoqin) {
		return super.findPage(page, aomKaoqin);
	}
	
	@Transactional(readOnly = false)
	public void save(AomKaoqin aomKaoqin) {
		super.save(aomKaoqin);
	}
	
	@Transactional(readOnly = false)
	public void delete(AomKaoqin aomKaoqin) {
		super.delete(aomKaoqin);
	}
	
}