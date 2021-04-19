package com.dhc.rad.modules.pzMenu.entity;

import com.dhc.rad.common.persistence.DataEntity;

/**
 * @author 10951
 */
public class PzMenu extends DataEntity<PzMenu> {

    private static final long serialVersionUID = 1L;


    /**
     *菜单名称
     */
    private String menuName;
    /**
     *菜单描述
     */
    private String menuDescription;
    /**
     *菜单图片地址
     */
    private String menuImgUrl;
    /**
     *套餐限量是否 0不限,1限制 默认0
     */
    private String menuLimited;
    /**
     *
     * 套餐余量
     */
    private Integer menuCount;
    /**
     *套餐规格 0小份 1默认 2大份
     */
    private String menuType;
    /**
     *菜单状态 0保存可修改,1提交,2审核不通过,3审核通过
     */
    private Integer menuStatus;
    /**
     *菜单是否上架 0未上架 1上架 默认0
     */
    private Integer menuUp;
    /**
     *审核原因
     */
    private String examineInfo;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getMenuImgUrl() {
        return menuImgUrl;
    }

    public void setMenuImgUrl(String menuImgUrl) {
        this.menuImgUrl = menuImgUrl;
    }

    public String getMenuLimited() {
        return menuLimited;
    }

    public void setMenuLimited(String menuLimited) {
        this.menuLimited = menuLimited;
    }

    public Integer getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(Integer menuCount) {
        this.menuCount = menuCount;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(Integer menuStatus) {
        this.menuStatus = menuStatus;
    }

    public Integer getMenuUp() {
        return menuUp;
    }

    public void setMenuUp(Integer menuUp) {
        this.menuUp = menuUp;
    }

    public String getExamineInfo() {
        return examineInfo;
    }

    public void setExamineInfo(String examineInfo) {
        this.examineInfo = examineInfo;
    }
}
