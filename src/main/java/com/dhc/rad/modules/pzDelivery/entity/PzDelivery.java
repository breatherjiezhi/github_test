package com.dhc.rad.modules.pzDelivery.entity;

import com.dhc.rad.common.persistence.DataEntity;

/**
 * @author 10951
 */
public class PzDelivery extends DataEntity<PzDelivery> {

    /**
     * 餐厅id
     */
    private String restaurantId;

    /**
     * 服务单元id
     */
    private String serviceUnitId;


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
}
