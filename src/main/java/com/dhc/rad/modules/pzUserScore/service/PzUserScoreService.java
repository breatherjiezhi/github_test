package com.dhc.rad.modules.pzUserScore.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;
import com.dhc.rad.modules.pzUserScore.dao.PzUserScoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzUserScoreService extends CrudService<PzUserScoreDao, PzUserScore> {
    
    @Autowired
    private PzUserScoreDao pzUserScoreDao;

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzUserScore pzUserScore) {
        if(ObjectUtils.isNotEmpty(pzUserScore)){
            if(ObjectUtils.isNotEmpty(pzUserScoreDao.getByUserIdAndRestaurantId(pzUserScore.getUserId(),pzUserScore.getRestaurantId()))){
                pzUserScore.preUpdate();
                return pzUserScoreDao.update(pzUserScore);
            }else{
                pzUserScore.preInsert();
                return pzUserScoreDao.insert(pzUserScore);
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return pzUserScoreDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public PzUserScore getByUserIdAndRestaurantId(String userId, String restaurantId) {
        return pzUserScoreDao.getByUserIdAndRestaurantId(userId,restaurantId);
    }
}
