package com.dhc.rad.modules.sys.entity;

import com.dhc.rad.common.persistence.DataEntity;

public class ChangeInfo   extends DataEntity<ChangeInfo> {


    /**
     * 1-人员部门变更流程
     */
    private Integer changeType;

    /**
     * 原始数据
     */
    private String oldValue;

    /**
     * 新数据
     */
    private String newValue;

    /**
     * 1-草稿，2-提交审核，3-审核不通过，4-审核通过
     */
    private Integer applyStatus;


    //展示字段
    private String oldName;
    private String newName;

    //用于查询
    private String officeId;



    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
}
