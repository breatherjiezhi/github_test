package com.dhc.rad.modules.pzMenuContent.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzMenuContent.dao.PzMenuContentDao;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PzMenuContentService extends CrudService<PzMenuContentDao, PzMenuContent> {



    @Autowired
    private PzMenuContentDao pzMenuContentDao;


    public Page<PzMenuContent> searchPage(Page<PzMenuContent> pzMenuContentPage, PzMenuContent pzMenuContent) {
        pzMenuContent.setPage(pzMenuContentPage);
        List<PzMenuContent> list = pzMenuContentDao.findList(pzMenuContent);
        pzMenuContentPage.setList(list);
        return pzMenuContentPage;
    }


    public  List<PzMenuContent> findListByMenuId(String menuId){
        return pzMenuContentDao.findListByMenuId(menuId);
    }


}
