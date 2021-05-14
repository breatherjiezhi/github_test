package com.dhc.rad.modules.pzBoxCode.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzBoxCode.entity.PzBoxCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzBoxCodeDao extends CrudDao<PzBoxCode> {

    /**
     * 批量删除(逻辑删除)
     *
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);


    PzBoxCode findByBoxCode(@Param("id")String id,@Param("boxCode") String boxCode,@Param("restaurantId")String restaurantId);


}
