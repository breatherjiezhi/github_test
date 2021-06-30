package com.dhc.rad.modules.pzCensus.service;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.pzCensus.dao.PzCensusDao;
import com.dhc.rad.modules.pzCensus.entity.PzCensus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PzCensusService extends CrudService<PzCensusDao, PzCensus> {
    @Autowired
    private PzCensusDao pzCensusDao;


    /**
     * 每日订单 按服务单元查看
     * @param pzCensusPage
     * @param pzCensus  RESTAURANT_ID当前餐厅id,EAT_DATE=当天日期或某天日期
     * @return
     */
    public Page<PzCensus> searchPage(Page<PzCensus> pzCensusPage, PzCensus pzCensus) {
        pzCensus.setPage(pzCensusPage);
        List<PzCensus> list = pzCensusDao.findList(pzCensus);
        pzCensusPage.setList(list);
        return pzCensusPage;
    }

    /**
     * 按每天统计套餐数量
     * @param pzCensus RESTAURANT_ID = '17cdd4d1f96b4afa89cd11bec6be6d96',beginDate开始时间,endDate结束时间
     * @return
     */
    public Page<PzCensus> findCensusPage(Page<PzCensus> pzCensusPage,PzCensus pzCensus){
        pzCensus.setPage(pzCensusPage);
        List<PzCensus> list =  pzCensusDao.findCensusList(pzCensus);
        pzCensusPage.setList(list);
        return pzCensusPage;

    }


    /**
     * 按每天统计套餐数量
     * @param pzCensus RESTAURANT_ID = '17cdd4d1f96b4afa89cd11bec6be6d96',beginDate开始时间,endDate结束时间
     * @return
     */
    public List<PzCensus> findCensusSum(PzCensus pzCensus){
        List<PzCensus> list =  pzCensusDao.findCensusSum(pzCensus);
        return list;

    }


    /**
     * 按人员统计查询
     * @param restaurantId
     * @param officeId
     * @param beginDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<Map<String,Object>> findUserCensusPage(String restaurantId,String officeId,
                                                      String beginDate,String endDate,
                                                      Integer pageNo,Integer pageSize){
        return   pzCensusDao.selectUserCensus(restaurantId,officeId,beginDate,endDate,pageNo,pageSize);
    }



    /**
     * 按人员统计查询
     * @param restaurantId
     * @param officeId
     * @param beginDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<Map<String,Object>> findDeptCensusPage(String restaurantId,String officeId,
                                                       String beginDate,String endDate,
                                                       Integer pageNo,Integer pageSize){
        return   pzCensusDao.selectDeptCensus(restaurantId,officeId,beginDate,endDate,pageNo,pageSize);
    }

    /**
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<String> findEatDate(String beginDate,String endDate){
        return pzCensusDao.findEatDate(beginDate,endDate);
    }
}
