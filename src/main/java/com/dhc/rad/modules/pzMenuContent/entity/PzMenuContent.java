package com.dhc.rad.modules.pzMenuContent.entity;

import com.dhc.rad.common.persistence.DataEntity;

import java.util.Date;

/**
 * @description 套餐详细
 * @author
 * @date 2021-05-17
 */
public class PzMenuContent extends DataEntity<PzMenuContent> {
    private static final long serialVersionUID = 1L;
    /**
     * 套餐id
     */
    private String menuId;

    /**
     * 套餐描述
     */
    private String menuDetail;

    /**
     * 套餐日期
     */
    private String eatDate;

    /**
     * 套餐日期对应周几
     */
    private String eatWeek;

    /**
     * 套餐是否限量 0：不限量 1：限量
     */
    private String menuLimited;

    /**
     * 套餐余量
     */
    private Integer menuCount;

    /**
     *版本号
     */
    private Integer version;

    public String getMenuLimited() {
        return menuLimited;
    }

    public void setMenuLimited(String menuLimited) {
        this.menuLimited = menuLimited;
    }

    public Integer getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(Integer menuCount) {
        this.menuCount = menuCount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PzMenuContent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getEatWeek() {
        return eatWeek;
    }

    public void setEatWeek(String eatWeek) {
        this.eatWeek = eatWeek;
    }
}
