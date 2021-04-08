/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.dao;


import com.dhc.rad.common.persistence.CrudDao;
import com.dhc.rad.common.persistence.annotation.MyBatisDao;
import com.dhc.rad.modules.gen.entity.GenTable;
import com.dhc.rad.modules.gen.entity.GenTableColumn;

import java.util.List;

/**
 * 机构DAO接口
 * @author ZN
 * @version 2014-05-16
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

    /**
     * 查询表列表
     * @param genTable
     * @return
     */
    List<GenTable> findTableList(GenTable genTable);

    /**
     * 获取数据表字段
     * @param genTable
     * @return
     */
    List<GenTableColumn> findTableColumnList(GenTable genTable);

    /**
     * 获取数据表主键
     * @param genTable
     * @return
     */
    List<String> findTablePK(GenTable genTable);
}
