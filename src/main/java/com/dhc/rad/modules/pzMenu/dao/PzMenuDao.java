package com.dhc.rad.modules.pzMenu.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzMenuDao extends CrudDao<PzMenu> {
    /**
     * 批量删除(逻辑删除)
     *
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);


    Integer findMenuCount(@Param("id") String id);

    Integer findVersion(@Param("id") String id);

    Integer updateMenuCount(PzMenu pzMenu);

    List<PzMenu> findListByRid(@Param("restaurantId")String restaurantId,@Param("eatDate")String eatDate);

    //Integer submitPzMenu(PzMenu pzMenu);
}
