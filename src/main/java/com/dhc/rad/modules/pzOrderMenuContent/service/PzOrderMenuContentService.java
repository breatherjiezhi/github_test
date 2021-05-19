package com.dhc.rad.modules.pzOrderMenuContent.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzOrderMenuContent.dao.PzOrderMenuContentDao;
import com.dhc.rad.modules.pzOrderMenuContent.entity.PzOrderMenuContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PzOrderMenuContentService extends CrudService<PzOrderMenuContentDao, PzOrderMenuContent> {

    @Autowired
    private PzOrderMenuContentDao pzOrderMenuContentDao;

    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {
        return  pzOrderMenuContentDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzOrderMenuContent pzOrderMenuContent) {
        if(ObjectUtils.isNotEmpty(pzOrderMenuContent)){
            if(StringUtils.isNotBlank(pzOrderMenuContent.getId())){
                pzOrderMenuContent.preUpdate();
                return pzOrderMenuContentDao.update(pzOrderMenuContent);
            }else{
                pzOrderMenuContent.preInsert();
                return pzOrderMenuContentDao.insert(pzOrderMenuContent);
            }
        }
        return 0;
    }
}
