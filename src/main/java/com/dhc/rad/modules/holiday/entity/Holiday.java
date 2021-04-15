package com.dhc.rad.modules.holiday.entity;

import com.dhc.rad.common.persistence.DataEntity;
import com.dhc.rad.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author 10951
 */
public class Holiday extends DataEntity<Holiday> {


    private static final long serialVersionUID = 1L;

    /**
     * 日期类型，例如：端午/清明/春节。。。。。
     */
    private String holidayType;

    /**
     * 假期日期
     */
    private Date holidayDate;


    //用于查询字段
    private String beginDate;
    private String endDate;




    @ExcelField(title="假期类型", align=2, sort=2)
    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelField(title="日期", align=2, sort=3)
    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }


    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
