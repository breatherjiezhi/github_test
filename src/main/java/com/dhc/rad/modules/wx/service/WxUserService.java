package com.dhc.rad.modules.wx.service;


import com.dhc.rad.modules.sys.dao.ChangeInfoDao;
import com.dhc.rad.modules.sys.dao.UserDao;
import com.dhc.rad.modules.sys.entity.ChangeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WxUserService {
    private UserDao userDao;

    private ChangeInfoDao changeInfoDao;


    @Transactional(readOnly = false)
    public Integer applyChange(String id,String applyFlag){
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setId(id);
        Integer flag = 0;
        if("true".equals(applyFlag)){
            changeInfo.setApplyStatus(4);
            changeInfo.preUpdate();
            flag += changeInfoDao.update(changeInfo);
            ChangeInfo ci = changeInfoDao.get(id);
            flag += userDao.updateOfficeIdById(ci.getCreateBy().getId(),ci.getNewValue());
        }else{
            changeInfo.setApplyStatus(3);
            changeInfo.preUpdate();
            flag += changeInfoDao.update(changeInfo);
        }
        return flag;
    }






}
