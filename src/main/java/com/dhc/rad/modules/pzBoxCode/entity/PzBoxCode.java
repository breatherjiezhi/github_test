package com.dhc.rad.modules.pzBoxCode.entity;

import com.dhc.rad.common.persistence.DataEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 10951
 */
public class PzBoxCode extends DataEntity<PzBoxCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 餐厅id
     */
    private String restaurantId;

    /**
     * 服务单元id
     */
    private String serviceUnitId;

    /**
     * 箱子编码
     */
    private String boxCode;

    /**
     * 箱子中文名称
     */
    private String boxCnName;


    /**
     * 餐厅名称
     */
    private String restaurantName;

    /**
     * 服务单元名称
     */
    private String serviceUnit;

    /**
     * 投料点名称
     */
    private String areaName;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getServiceUnitId() {
        return serviceUnitId;
    }

    public void setServiceUnitId(String serviceUnitId) {
        this.serviceUnitId = serviceUnitId;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getBoxCnName() {
        return boxCnName;
    }

    public void setBoxCnName(String boxCnName) {
        this.boxCnName = boxCnName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
