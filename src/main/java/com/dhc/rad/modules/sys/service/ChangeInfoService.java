package com.dhc.rad.modules.sys.service;


import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.sys.dao.ChangeInfoDao;
import com.dhc.rad.modules.sys.entity.ChangeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChangeInfoService extends CrudService<ChangeInfoDao, ChangeInfo> {
    @Autowired
    private ChangeInfoDao changeInfoDao;



    @Transactional(readOnly = false)
    public Integer insert(ChangeInfo changeInfo) {
        if (ObjectUtils.isNotEmpty(changeInfo)) {
            changeInfo.preInsert();
            return   changeInfoDao.insert(changeInfo);
        }
        return 0;
    }


    public List<ChangeInfo> findApplyList(ChangeInfo  changeInfo){
        return changeInfoDao.findApplyList(changeInfo);
    }



    @Transactional(readOnly = false)
    public Integer update(ChangeInfo changeInfo) {
        if (ObjectUtils.isNotEmpty(changeInfo)) {
            changeInfo.preUpdate();
            return   changeInfoDao.update(changeInfo);
        }
        return 0;
    }
}
