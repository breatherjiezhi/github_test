package com.dhc.rad.modules.sys.entity;

import com.dhc.rad.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 10951
 */
public class UserScore extends DataEntity<UserScore> {

    private static final long serialVersionUID = 1L;

    /**
     * userId:用户编号
     */
    private String userId;
    /**
     * restaurantId:餐厅编号
     */
    private String restaurantId;
    /**
     * canteenIntegral:餐厅积分
     */
    private BigDecimal canteenIntegral;
    /**
     * createBy
     */
    private String createBy;
    /**
     * createDate
     */
    private Date createDate;
    /**
     * updateBy
     */
    private String updateBy;
    /**
     * updateDate
     */
    private Date updateDate;
    /**
     * remarks
     */
    private String remarks;

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
