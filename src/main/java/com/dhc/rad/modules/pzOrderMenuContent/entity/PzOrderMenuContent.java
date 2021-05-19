package com.dhc.rad.modules.pzOrderMenuContent.entity;

import com.dhc.rad.common.persistence.DataEntity;

public class PzOrderMenuContent extends DataEntity<PzOrderMenuContent> {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 菜单明细id
     */
    private String contentId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
