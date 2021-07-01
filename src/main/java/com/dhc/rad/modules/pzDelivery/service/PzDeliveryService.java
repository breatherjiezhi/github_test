package com.dhc.rad.modules.pzDelivery.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzDelivery.entity.PzDelivery;
import com.dhc.rad.modules.pzDelivery.dao.PzDeliveryDao;
import com.dhc.rad.modules.pzDelivery.entity.PzDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzDeliveryService extends CrudService<PzDeliveryDao, PzDelivery> {

    @Autowired
    private PzDeliveryDao pzDeliveryDao;

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzDelivery pzDelivery) {
        if(ObjectUtils.isNotEmpty(pzDelivery)){
            if(StringUtils.isNotBlank(pzDelivery.getId())){
                pzDelivery.preUpdate();
                return pzDeliveryDao.update(pzDelivery);
            }else{
                pzDelivery.preInsert();
                return pzDeliveryDao.insert(pzDelivery);
            }
        }
        return 0;
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdateDelivery(List<PzDelivery> pzDeliveryList) {
        Integer result = 0;
        for (PzDelivery pzDelivery : pzDeliveryList) {
            if(pzDelivery!=null){
                if(pzDeliveryDao.getDelivery(pzDelivery)!=null){
                    pzDelivery.preUpdate();
                    Integer updateDelivery = pzDeliveryDao.updateDelivery(pzDelivery);
                    if(updateDelivery > 0){
                        result ++;
                    }
                }else{
                    pzDelivery.preInsert();
                    int insert = pzDeliveryDao.insert(pzDelivery);
                    if(insert > 0){
                        result ++;
                    }
                }
            }else{
                return 0;
            }
        }

        return result;

    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return pzDeliveryDao.deleteByIds(ids);
    }

    public List<Map<String,Object>> findInfoByAreaId(String areaId,String restaurantId,String eatDate){

        return pzDeliveryDao.findInfoByAreaId(areaId,restaurantId,eatDate);
    }
}
