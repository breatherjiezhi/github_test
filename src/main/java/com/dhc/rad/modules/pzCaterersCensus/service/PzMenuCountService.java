package com.dhc.rad.modules.pzCaterersCensus.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzCaterersCensus.dao.PzMenuCountDao;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzMenuCount;
import com.dhc.rad.modules.pzCaterersCensus.entity.PzServiceUnitCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PzMenuCountService extends CrudService<PzMenuCountDao, PzMenuCount>{


    @Autowired
    private PzMenuCountDao pzMenuCountDao;


    public  Page<PzMenuCount> selectNextWeekCount(Page<PzMenuCount> page,PzMenuCount pzMenuCount){
        pzMenuCount.setPage(page);
        page.setList(pzMenuCountDao.selectNextWeekCount(pzMenuCount));

        return page;
    }




}
