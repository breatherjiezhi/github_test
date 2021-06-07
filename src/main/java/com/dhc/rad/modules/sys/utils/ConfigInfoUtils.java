package com.dhc.rad.modules.sys.utils;

import com.dhc.rad.common.utils.SpringContextHolder;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.sys.dao.ConfigInfoDao;
import com.dhc.rad.modules.sys.entity.ConfigInfo;

public class ConfigInfoUtils {


    private static ConfigInfoDao configInfoDao = SpringContextHolder.getBean(ConfigInfoDao.class);


    public static String getConfigVal(String key){
        if(StringUtils.isNotBlank(key)){
            ConfigInfo configInfo =  configInfoDao.get(key);
            return configInfo!=null?configInfo.getConfigValue():null;
        }else{
            return null;
        }

    }

}
