package com.dhc.rad.modules.pzCaterersCensus.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzCaterersCensus.dao.PzServiceUnitCountDao;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzServiceUnitCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PzServiceUnitCountService extends CrudService<PzServiceUnitCountDao, PzServiceUnitCount> {


    @Autowired
    private PzServiceUnitCountDao pzServiceUnitCountDao;


    public Page<PzServiceUnitCount> selectServiceUnitCount(Page<PzServiceUnitCount> page, PzServiceUnitCount pzServiceUnitCount){
        pzServiceUnitCount.setPage(page);
        page.setList( pzServiceUnitCountDao.selectServiceUnitCount(pzServiceUnitCount));
        return page;
    }




}
