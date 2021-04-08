/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.service;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.BaseService;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.gen.dao.GenDataBaseDictDao;
import com.dhc.rad.modules.gen.dao.GenTableColumnDao;
import com.dhc.rad.modules.gen.dao.GenTableDao;
import com.dhc.rad.modules.gen.entity.GenTable;
import com.dhc.rad.modules.gen.entity.GenTableColumn;
import com.dhc.rad.modules.gen.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构Service
 * @author ZN
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {


    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao ;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;


    public Page<GenTable> findPage(Page<GenTable> page, GenTable entity) {
        entity.setPage(page);
        page.setList(genTableDao.findList(entity));
        return page;
    }

    public List<String> findNameList(){
        return genTableDao.findNameList(new GenTable());
    }

    @Transactional(readOnly = false)
    public boolean delete(String[] ids) {
        boolean b = false;
        if (ids == null) {
            return b;
        }
        for (String idItem : ids) {
           GenTable genTable=new GenTable(idItem);
           genTableDao.delete(genTable);
        }

        b = true;
        return b;
    }


    public GenTable get(String id) {
        GenTable genTable = genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    public GenTable getTableFormDb(GenTable genTable){
        // 如果有表名，则获取物理表
        if (StringUtils.isNotBlank(genTable.getName())){

            List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
            if (list.size() > 0){

                // 如果是新增，初始化表属性
                if (StringUtils.isBlank(genTable.getId())){
                    genTable = list.get(0);
                    // 设置字段说明
                    if (StringUtils.isBlank(genTable.getComments())){
                        genTable.setComments(genTable.getName());
                    }
                    genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
                }

                // 添加新列
                List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
                for (GenTableColumn column : columnList){
                    boolean b = false;
                    for (GenTableColumn e : genTable.getColumnList()){
                        if (e.getName().equals(column.getName())){
                            b = true;
                        }
                    }
                    if (!b){
                        genTable.getColumnList().add(column);
                    }
                }

                // 删除已删除的列
                for (GenTableColumn e : genTable.getColumnList()){
                    boolean b = false;
                    for (GenTableColumn column : columnList){
                        if (column.getName().equals(e.getName())){
                            b = true;
                        }
                    }
                    if (!b){
                        e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
                    }
                }

                // 获取主键
                genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));

                // 初始化列属性字段
                GenUtils.initColumnField(genTable);

            }
        }
        return genTable;
    }

    /**
     * 获取物理数据表列表
     * @param genTable
     * @return
     */
    public List<GenTable> findTableListFormDb(GenTable genTable){
        return genDataBaseDictDao.findTableList(genTable);
    }

    /**
     * 验证表名是否可用，如果已存在，则返回false
     * @param tableName
     * @return
     */
    public boolean checkTableName(String tableName){
        if (StringUtils.isBlank(tableName)){
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> list = genTableDao.findList(genTable);
        return list.size() == 0;
    }

    @Transactional(readOnly = false)
    public void save(GenTable genTable) {
        if (StringUtils.isBlank(genTable.getId())){
            genTable.preInsert();
            genTableDao.insert(genTable);
        }else{
            genTable.preUpdate();
            genTableDao.update(genTable);
        }
        // 保存列
        for (GenTableColumn column : genTable.getColumnList()){
            column.setGenTable(genTable);
            if (StringUtils.isBlank(column.getId())){
                column.preInsert();
                genTableColumnDao.insert(column);
            }else{
                column.preUpdate();
                genTableColumnDao.update(column);
            }
        }
    }


    public List<GenTable> findAll() {
        return genTableDao.findAllList(new GenTable());
    }










}
