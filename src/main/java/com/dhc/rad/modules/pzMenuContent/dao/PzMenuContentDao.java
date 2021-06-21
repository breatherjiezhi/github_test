package com.dhc.rad.modules.pzMenuContent.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface PzMenuContentDao extends CrudDao<PzMenuContent> {


    /**
     * 批量删除(逻辑删除)
     *
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);


    Integer deleteByMenuId(@Param("menuId")String menuId);

    List<PzMenuContent> findListByMenuId(@Param("menuId")String menuId);

    /**
     *更新套餐余量
     * @param contentId version
     *@param  version
     * @return Integer
     */
    Integer updateMenuContentCount(@Param("contentId") String contentId, @Param("version") Integer version);
}
