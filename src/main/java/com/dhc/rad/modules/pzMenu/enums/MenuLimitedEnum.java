package com.dhc.rad.modules.pzMenu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 10951
 */

@Getter
@AllArgsConstructor
public enum MenuLimitedEnum {

    /**
     * 不限量
     */
    MENU_LIMITED_NO("不限量", 0),

    /**
     * 限量
     */
    MENU_LIMITED_YES("限量", 1),


    ;

    final String name;
    final int category;

    /**
     * 匹配枚举值
     *
     * @param name 名称
     * @return
     */
    public static MenuLimitedEnum of(String name) {
        if (name == null) {
            return null;
        }
        MenuLimitedEnum[] values = MenuLimitedEnum.values();
        for (MenuLimitedEnum smsEnum : values) {
            if (smsEnum.name.equals(name)) {
                return smsEnum;
            }
        }
        return null;
    }
}
