package com.dhc.rad.modules.pzBoxCode.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzBoxCode.dao.PzBoxCodeDao;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzBoxCodeService extends CrudService<PzBoxCodeDao, PzBoxCode> {
    
    @Autowired
    private PzBoxCodeDao pzBoxCodeDao;

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzBoxCode pzBoxCode) {
        if(ObjectUtils.isNotEmpty(pzBoxCode)){
            if(StringUtils.isNotBlank(pzBoxCode.getId())){
                pzBoxCode.preUpdate();
                return pzBoxCodeDao.update(pzBoxCode);
            }else{
                pzBoxCode.preInsert();
                return pzBoxCodeDao.insert(pzBoxCode);
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return pzBoxCodeDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public PzBoxCode findByBoxCode(String id,String boxCode,String restaurantId) {
        return pzBoxCodeDao.findByBoxCode(id,boxCode,restaurantId);
    }
}
