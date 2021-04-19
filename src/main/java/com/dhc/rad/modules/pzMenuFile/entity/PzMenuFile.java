package com.dhc.rad.modules.pzMenuFile.entity;

import com.dhc.rad.common.persistence.DataEntity;

/**
 * @author 10951
 */
public class PzMenuFile extends DataEntity<PzMenuFile> {

    private static final long serialVersionUID = 1L;

    /**
     *menuId
     */
    private String menuId;
    /**
     *文件名称
     */
    private String fileName;
    /**
     *文件路径
     */
    private String filePath;
    /**
     *版本
     */
    private String version;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
