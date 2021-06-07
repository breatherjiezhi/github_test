package com.dhc.rad.modules.sys.service;


import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.modules.sys.dao.ConfigInfoDao;
import com.dhc.rad.modules.sys.entity.ConfigInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConfigInfoService  extends CrudService<ConfigInfoDao, ConfigInfo> {









}
