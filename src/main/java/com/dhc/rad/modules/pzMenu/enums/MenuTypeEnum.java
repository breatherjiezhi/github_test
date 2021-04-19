package com.dhc.rad.modules.pzMenu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 10951
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    /**
     * 套餐规格：小份
     */
    MENU_TYPE_SMALL("小份", 0),

    /**
     * 套餐规格：默认
     */
    MENU_LIMITED_DEFAULT("默认", 1),

    /**
     * 套餐规格：大份
     */
    MENU_LIMITED_BIG("大份", 2),


    ;

    final String name;
    final int category;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return
     */
    public static MenuTypeEnum of(String name) {
        if (name == null) {
            return null;
        }
        MenuTypeEnum[] values = MenuTypeEnum.values();
        for (MenuTypeEnum smsEnum : values) {
            if (smsEnum.name.equals(name)) {
                return smsEnum;
            }
        }
        return null;
    }
}
