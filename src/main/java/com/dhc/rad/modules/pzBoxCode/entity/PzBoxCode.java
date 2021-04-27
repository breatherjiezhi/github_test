package com.dhc.rad.modules.pzBoxCode.entity;

import com.dhc.rad.common.persistence.DataEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 10951
 */
@Setter
@Getter
public class PzBoxCode extends DataEntity<PzBoxCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 餐厅id
     */
    private String restaurantId;

    /**
     * 服务单元id
     */
    private String serviceUnitId;

    /**
     * 箱子编码
     */
    private String boxCode;

    /**
     * 箱子中文名称
     */
    private String boxCnName;


}
