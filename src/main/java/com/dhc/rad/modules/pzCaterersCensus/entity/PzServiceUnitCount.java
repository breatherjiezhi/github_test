package com.dhc.rad.modules.pzCaterersCensus.entity;

import com.dhc.rad.common.persistence.DataEntity;

import java.util.List;

public class PzServiceUnitCount  extends DataEntity<PzServiceUnitCount> {


    private String serviceUnitId;

    private String serviceUnitName;


    /**
     * 以下字段用于查询
     */
    private String eatDate;

    private String restaurantId;

    private String noEatDate;


    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getNoEatDate() {
        return noEatDate;
    }

    public void setNoEatDate(String noEatDate) {
        this.noEatDate = noEatDate;
    }

    private List<PzMenuCount> pzMenuCountList;


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

    public List<PzMenuCount> getPzMenuCountList() {
        return pzMenuCountList;
    }

    public void setPzMenuCountList(List<PzMenuCount> pzMenuCountList) {
        this.pzMenuCountList = pzMenuCountList;
    }
}
