package com.dhc.rad.modules.pzOrderContent.entity;

import com.dhc.rad.common.persistence.DataEntity;

public class PzOrderContent extends DataEntity<PzOrderContent> {

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
