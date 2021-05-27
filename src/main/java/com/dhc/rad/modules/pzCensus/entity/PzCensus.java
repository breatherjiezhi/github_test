package com.dhc.rad.modules.pzCensus.entity;

import com.dhc.rad.common.persistence.DataEntity;

public class PzCensus extends DataEntity<PzCensus> {
    private String eatDate;

    private String eatWeek;

    private String restaurantId;

    private String restaurantName;

    private String serviceUnitId;

    private String serviceUnitName;

    private String areaName;

    private String countA;

    private String countB;

    private String countC;

    private String countD;
    private String countE;
    private String countF;


    //用于查询
    private String beginDate;

    private String endDate;


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

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getServiceUnitId() {
        return serviceUnitId;
    }

    public void setServiceUnitId(String serviceUnitId) {
        this.serviceUnitId = serviceUnitId;
    }

    public String getServiceUnitName() {
        return serviceUnitName;
    }

    public void setServiceUnitName(String serviceUnitName) {
        this.serviceUnitName = serviceUnitName;
    }

    public String getCountA() {
        return countA;
    }

    public void setCountA(String countA) {
        this.countA = countA;
    }

    public String getCountB() {
        return countB;
    }

    public void setCountB(String countB) {
        this.countB = countB;
    }

    public String getCountC() {
        return countC;
    }

    public void setCountC(String countC) {
        this.countC = countC;
    }

    public String getCountD() {
        return countD;
    }

    public void setCountD(String countD) {
        this.countD = countD;
    }

    public String getCountE() {
        return countE;
    }

    public void setCountE(String countE) {
        this.countE = countE;
    }

    public String getCountF() {
        return countF;
    }

    public void setCountF(String countF) {
        this.countF = countF;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
