package com.dhc.rad.modules.pzOrder.entity;

import com.dhc.rad.common.persistence.DataEntity;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;

import java.math.BigDecimal;

/**
 * @author 10951
 */
public class PzOrder extends DataEntity<PzOrder> {

    private static final long serialVersionUID = 1L;

    /**
     *菜单名称
     */
    private String userId;

    /**
     *菜单名称
     */
    private String menuId;

    /**
     *服务单元编号
     */
    private String serviceUnitId;

    /**
     *餐厅编号
     */
    private String restaurantId;

    /**
     *菜单名称
     */
    private String menuType;

    /**
     *套餐所需积分
     */
    private BigDecimal menuIntegral;

    /**
     *吃饭日期
     */
    private String eatDate;

    /**
     *不吃饭日期
     */
    private String noEatDate;

    public String getNoEatDate() {
        return noEatDate;
    }

    public void setNoEatDate(String noEatDate) {
        this.noEatDate = noEatDate;
    }

    public BigDecimal getMenuIntegral() {
        return menuIntegral;
    }

    public void setMenuIntegral(BigDecimal menuIntegral) {
        this.menuIntegral = menuIntegral;
    }

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getServiceUnitId() {
        return serviceUnitId;
    }

    public void setServiceUnitId(String serviceUnitId) {
        this.serviceUnitId = serviceUnitId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
}
