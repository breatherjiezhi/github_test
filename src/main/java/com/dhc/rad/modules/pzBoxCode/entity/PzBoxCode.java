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
}
