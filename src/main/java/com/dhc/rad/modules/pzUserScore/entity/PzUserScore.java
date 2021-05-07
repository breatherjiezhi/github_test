package com.dhc.rad.modules.pzUserScore.entity;

import com.dhc.rad.common.persistence.DataEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 10951
 */

public class PzUserScore extends DataEntity<PzUserScore> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 餐厅id
     */
    private String restaurantId;

    /**
     * 个人餐厅所属积分
     */
    private BigDecimal canteenIntegral;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getCanteenIntegral() {
        return canteenIntegral;
    }

    public void setCanteenIntegral(BigDecimal canteenIntegral) {
        this.canteenIntegral = canteenIntegral;
    }
}
