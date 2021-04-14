package com.dhc.rad.modules.holiday.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.holiday.entity.Holiday;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface HolidayDao extends CrudDao<Holiday> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

    /**
     * 根据日期查询假期信息
     * @param holidayDate
     * @return Holiday
     */
    Holiday getByDate(@Param("holidayDate") String holidayDate);

}
