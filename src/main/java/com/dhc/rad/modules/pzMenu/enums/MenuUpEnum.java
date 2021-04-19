package com.dhc.rad.modules.pzMenu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 10951
 */
@Getter
@AllArgsConstructor
public enum MenuUpEnum {
    /**
     *菜单是否上架 0 未上架
     */
    MENU_UP_NO_ON_SALE("未上架", 0),

    /**
     * 菜单是否上架 1 上架
     */
    MENU_UP_ON_SALE("上架", 1),

    ;

    final String name;
    final int category;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return
     */
    public static MenuUpEnum of(String name) {
        if (name == null) {
            return null;
        }
        MenuUpEnum[] values = MenuUpEnum.values();
        for (MenuUpEnum smsEnum : values) {
            if (smsEnum.name.equals(name)) {
                return smsEnum;
            }
        }
        return null;
    }
}
