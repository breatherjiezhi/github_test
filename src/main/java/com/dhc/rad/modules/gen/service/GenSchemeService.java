/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.service;


import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.BaseService;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.gen.dao.GenSchemeDao;
import com.dhc.rad.modules.gen.dao.GenTableColumnDao;
import com.dhc.rad.modules.gen.dao.GenTableDao;
import com.dhc.rad.modules.gen.entity.*;
import com.dhc.rad.modules.gen.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 机构Service
 * @author ZN
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService {

    @Autowired
    private GenSchemeDao genSchemeDao;
    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao ;


    public GenScheme get(String id) {
        return genSchemeDao.get(id);
    }

    public List<String> findNameList(){
        return genSchemeDao.findNameList(new GenScheme());
    }

    public Page<GenScheme> findPage(Page<GenScheme> page, GenScheme entity) {
        entity.setPage(page);
        page.setList(genSchemeDao.findList(entity));
        return page;
    }

    @Transactional(readOnly = false)
    public boolean delete(String[] ids) {
        boolean b = false;
        if (ids == null) {
            return b;
        }
        for (String idItem : ids) {
            GenScheme genScheme=new GenScheme(idItem);
            genSchemeDao.delete(genScheme);
        }

        b = true;
        return b;
    }

    @Transactional(readOnly = false)
    public String save(GenScheme genScheme) {
        if (genScheme.getCodeFlag()){
            genScheme.setFlag(ConstantUtils.code_making);
        } else {
            genScheme.setFlag(ConstantUtils.code_not_make);
        }
        if (StringUtils.isBlank(genScheme.getId())){
            genScheme.preInsert();
            genSchemeDao.insert(genScheme);
        }else{
            genScheme.preUpdate();
            genSchemeDao.update(genScheme);
        }
        if ("1".equals(genScheme.getFlag())){
           return generateCode(genScheme);
        }

        return "";

    }

    private String generateCode(GenScheme genScheme){

        StringBuilder result=new StringBuilder();

        // 查询主表及字段列
        GenTable genTable =genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));

        // 获取所有代码模板
        GenConfig config= GenUtils.getConfig();

        // 获取模板列表
        List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
        List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);

        // 如果有子表模板，则需要获取子表列表
        if (childTableTemplateList.size() > 0){
            GenTable parentTable = new GenTable();
            parentTable.setParentTable(genTable.getName());
            genTable.setChildList(genTableDao.findList(parentTable));
        }

        // 生成子表模板代码
        for (GenTable childTable : genTable.getChildList()){
            childTable.setParent(genTable);
            childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
            genScheme.setGenTable(childTable);
            Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
            for (GenTemplate tpl : childTableTemplateList){
                result.append(GenUtils.generateToFile(tpl, childTableModel, genScheme.getReplaceFile()));
            }
        }

        // 生成主表模板代码
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        for (GenTemplate tpl : templateList){
            result.append(GenUtils.generateToFile(tpl, model, genScheme.getReplaceFile()));
        }
        return result.toString();
    }







}
