package com.dhc.rad.modules.pzOrder.entity;

import com.dhc.rad.common.persistence.DataEntity;

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
     *菜单名称
     */
    private String menuType;


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
