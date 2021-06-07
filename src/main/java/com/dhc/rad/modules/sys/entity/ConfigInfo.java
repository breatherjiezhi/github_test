package com.dhc.rad.modules.sys.entity;

import com.dhc.rad.common.persistence.DataEntity;

public class ConfigInfo extends DataEntity<ConfigInfo> {

    private String configKey;

    private String configValue;


    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
