package com.dhc.rad.modules.pzCaterersCensus.entity;


import com.dhc.rad.common.persistence.DataEntity;

public class PzMenuCount extends DataEntity<PzMenuCount> {

    private String menuId;

    private String menuName;

    private Integer menuCount;




    /**
     * 以下字段用于查询
     */
    private String eatDate;

    private String restaurantId;

    private String noEatDate;

    private String serviceUnitId;

    public String getServiceUnitId() {
        return serviceUnitId;
    }

    public void setServiceUnitId(String serviceUnitId) {
        this.serviceUnitId = serviceUnitId;
    }

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


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(Integer menuCount) {
        this.menuCount = menuCount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
