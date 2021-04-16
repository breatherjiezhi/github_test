package com.dhc.rad.modules.sys.entity;

import com.dhc.rad.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

public class UserTemplate {
    private String no;		// 工号
    private Integer userIntegral; //用户积分

    @ExcelField(title="工号", align=2, sort=1)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @ExcelField(title="充值次数", align=2, sort=3)
    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }
}
