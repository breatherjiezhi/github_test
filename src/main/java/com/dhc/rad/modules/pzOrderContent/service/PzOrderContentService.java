package com.dhc.rad.modules.pzOrderContent.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzOrderContent.dao.PzOrderContentDao;
import com.dhc.rad.modules.pzOrderContent.entity.PzOrderContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PzOrderContentService extends CrudService<PzOrderContentDao, PzOrderContent> {

    @Autowired
    private PzOrderContentDao pzOrderContentDao;

    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {
        return  pzOrderContentDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzOrderContent pzOrderMenuContent) {
        if(ObjectUtils.isNotEmpty(pzOrderMenuContent)){
            if(StringUtils.isNotBlank(pzOrderMenuContent.getId())){
                pzOrderMenuContent.preUpdate();
                return pzOrderContentDao.update(pzOrderMenuContent);
            }else{
                pzOrderMenuContent.preInsert();
                return pzOrderContentDao.insert(pzOrderMenuContent);
            }
        }
        return 0;
    }

    @Transactional(readOnly = false)
    public PzOrderContent getByContentIdAndCreateBy(String contentId, String userId) {
        return pzOrderContentDao.getByContentIdAndCreateBy(contentId,userId);
    }
    @Transactional(readOnly = false)
    public Integer findCountByOrderId(String orderId, String userId, String eatFlag) {
        return pzOrderContentDao.findCountByOrderId(orderId,userId,eatFlag);
    }


}
