package com.dhc.rad.modules.wx.entity;

import com.dhc.rad.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * @author 10951
 */
public class OrderVo {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String userNo;

    private String menuName;

    private String menuType;

    private String serviceUnit;

    private String restaurantName;

    private String noEatDate;

    private String areaLocation;

    private String boxName;

    private boolean deliveryFlag;


    /**
     * 以下字段用于查询
     */
    private String eatDate;

    private String eatDates;

    private String officeId;

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getEatDates() {
        return eatDates;
    }

    public void setEatDates(String eatDates) {
        this.eatDates = eatDates;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getNoEatDate() {
        return noEatDate;
    }

    public void setNoEatDate(String noEatDate) {
        this.noEatDate = noEatDate;
    }

    public String getAreaLocation() {
        return areaLocation;
    }

    public void setAreaLocation(String areaLocation) {
        this.areaLocation = areaLocation;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public boolean isDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(boolean deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
}
