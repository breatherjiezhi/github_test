package com.dhc.rad.modules.pzMenu.entity;

import com.dhc.rad.common.persistence.DataEntity;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;

import java.util.List;

/**
 * @author 10951
 */
public class PzMenu extends DataEntity<PzMenu> {

    private static final long serialVersionUID = 1L;


    /**
     * 菜单名称
     */
    private String menuName;


    /**
     * 供餐商id officeid
     */
    private String restaurantId;
    /**
     * 菜单描述
     */
    private String menuDescription;
    /**
     * 菜单图片地址
     */
    private String menuImgUrl;
    /**
     * 套餐限量是否 0不限,1限制 默认0
     */
    private String menuLimited;
    /**
     * 套餐余量
     */
    private Integer menuCount;
    /**
     * 套餐规格 0小份 1默认 2大份
     */
    private String menuType;
    /**
     * 菜单状态 0保存可修改,1提交,2审核不通过,3审核通过并上架
     */
    private Integer menuStatus;

    /**
     * 审核原因
     */
    private String examineInfo;


    //菜单所属供餐上名称
    private String gcsName;


    //所属餐厅名称
    private String restaurantName;


    //套餐详细列表
    private List<PzMenuContent> pzMenuContentList;


    //套餐详细信息
    private  String  pzMenuContentString;


    // 版本号
    private int version;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

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


    public String getExamineInfo() {
        return examineInfo;
    }

    public void setExamineInfo(String examineInfo) {
        this.examineInfo = examineInfo;
    }

    public String getGcsName() {
        return gcsName;
    }

    public void setGcsName(String gcsName) {
        this.gcsName = gcsName;
    }


    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<PzMenuContent> getPzMenuContentList() {
        return pzMenuContentList;
    }

    public void setPzMenuContentList(List<PzMenuContent> pzMenuContentList) {
        this.pzMenuContentList = pzMenuContentList;
    }

    public String getPzMenuContentString() {
        return pzMenuContentString;
    }

    public void setPzMenuContentString(String pzMenuContentString) {
        this.pzMenuContentString = pzMenuContentString;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
