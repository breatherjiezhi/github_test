package com.dhc.rad.modules.pzScoreLog.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzScoreLog.dao.PzScoreLogDao;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzScoreLogService extends CrudService<PzScoreLogDao, PzScoreLog> {

    @Autowired
    private PzScoreLogDao pzScoreLogDao;

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzScoreLog pzScoreLog) {
        if (ObjectUtils.isNotEmpty(pzScoreLog)) {
            if (StringUtils.isNotBlank(pzScoreLog.getId())) {
                pzScoreLog.preUpdate();
                return pzScoreLogDao.update(pzScoreLog);
            } else {
                pzScoreLog.preInsert();
                return pzScoreLogDao.insert(pzScoreLog);
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return pzScoreLogDao.deleteByIds(ids);
    }

    public Page<PzScoreLog> findScoreById(Page<PzScoreLog> page, PzScoreLog pzScoreLog) {
        pzScoreLog.setPage(page);
        List<PzScoreLog> pzScoreLogList = pzScoreLogDao.findScoreById(pzScoreLog);
        page.setList(pzScoreLogList);
        return page;
    }
}
