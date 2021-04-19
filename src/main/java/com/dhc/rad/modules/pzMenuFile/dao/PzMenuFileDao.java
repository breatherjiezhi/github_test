package com.dhc.rad.modules.pzMenuFile.dao;

import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.pzMenuFile.entity.PzMenuFile;

import java.util.List;

/**
 * @author 10951
 */
@MyBatisDao
public interface PzMenuFileDao extends CrudDao<PzMenuFile> {

    /**
     * 批量删除(逻辑删除)
     * @param ids
     * @return Integer
     */
    Integer deleteByIds(List<String> ids);

    /**
     * 检查文件名称
     * @param fileName
     * @return List<PzMenuFile>
     */
    List<PzMenuFile> checkFileName(String fileName);
}
