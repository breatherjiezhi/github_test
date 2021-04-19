package com.dhc.rad.modules.pzMenu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 10951
 */
@Getter
@AllArgsConstructor
public enum MenuStatusEnum {

    /**
     * 菜单状态 0 保存可修改
     */
    MENU_STATUS_SAVEANDUPDATE("保存可修改", 0),

    /**
     * 菜单状态 1 未审核
     */
    MENU_STATUS_SUBMIT("未审核", 1),

    /**
     * 菜单状态 2 审核未通过
     */
    MENU_STATUS_NOPASS("审核未通过", 2),
    /**
     * 菜单状态 3 审核通过
     */
    MENU_STATUS_PASS("审核通过", 3),


    ;

    final String name;
    final int category;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return
     */
    public static MenuStatusEnum of(String name) {
        if (name == null) {
            return null;
        }
        MenuStatusEnum[] values = MenuStatusEnum.values();
        for (MenuStatusEnum smsEnum : values) {
            if (smsEnum.name.equals(name)) {
                return smsEnum;
            }
        }
        return null;
    }
}
