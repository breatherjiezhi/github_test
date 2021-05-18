package com.dhc.rad.modules.pzDelivery.entity;

import com.dhc.rad.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

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

    /**
     * 配送箱子id
     */

    private String boxId;

    /**
     * 送达日期
     */
    private String eatDate;

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

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }
}
