/**
 * Copyright &copy; 2019-2029 ZN All rights reserved.
 */
package com.dhc.rad.modules.gen.entity;


import com.dhc.rad.common.persistence.DataEntity;

/**
 * 代码生成方案Entity
 *
 * @author ZN
 * @version 2013-05-15
 */
public class GenScheme extends DataEntity<GenScheme> {

    private static final long serialVersionUID = 1L;
    private String name;    // 名称
    private String category;        // 分类
    private String packageName;        // 生成包路径
    private String moduleName;        // 生成模块名
    private String subModuleName;        // 生成子模块名
    private String functionName;        // 生成功能名
    private String functionNameSimple;        // 生成功能名（简写）
    private String functionAuthor;        // 生成功能作者
    private Boolean replaceFile;    // 是否替换现有文件    0：不替换；1：替换文件

    private GenTable genTable;        // 业务表名
    private String flag;    // 0：保存方案； 1：保存方案并生成代码

    private Boolean codeFlag;  //是否生成代码

    public GenTable getGenTable() {
        return genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }

    public Boolean getCodeFlag() {
        return codeFlag;
    }

    public void setCodeFlag(Boolean codeFlag) {
        this.codeFlag = codeFlag;
    }

    public GenScheme() {
        super();
    }

    public GenScheme(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionNameSimple() {
        return functionNameSimple;
    }

    public void setFunctionNameSimple(String functionNameSimple) {
        this.functionNameSimple = functionNameSimple;
    }

    public String getFunctionAuthor() {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getReplaceFile() {
        return replaceFile;
    }

    public void setReplaceFile(Boolean replaceFile) {
        this.replaceFile = replaceFile;
    }


}
