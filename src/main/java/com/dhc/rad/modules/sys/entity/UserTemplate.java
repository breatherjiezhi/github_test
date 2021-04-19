package com.dhc.rad.modules.sys.entity;

import com.dhc.rad.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

public class UserTemplate {
    private String loginName;		// 工号
    private Integer userIntegral; //用户积分

    @ExcelField(title="登录名", align=2, sort=1)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @ExcelField(title="充值次数", align=2, sort=3)
    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }
}
