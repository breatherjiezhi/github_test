/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import jdk.internal.dynalink.beans.StaticClass;
import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.dhc.rad.common.utils.PropertiesLoader;
import com.dhc.rad.common.utils.StringUtils;

/**
 * 全局配置类
 *
 * @author DHC
 * @version 2014-06-25
 */
public class Global {

    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("rad.properties");

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String SUCCESS = "1";
    public static final String ERROR = "0";
    public static final String WARNING = "2";

    /**
     * 菜单审核状态
     */
    //菜单状态 0 保存可修改
    public static final int MENU_STATUS_SAVEANDUPDATE = 0;
    // 菜单状态 1 未审核
    public static final  int MENU_STATUS_SUBMIT=1;
    //菜单状态 2 审核未通过
    public static final  int MENU_STATUS_NOPASS=2;
    //菜单状态 3 审核通过
    public static final  int MENU_STATUS_PASS=3;

    /**
     * 套餐限量标志
     */
    //不限量
    public static final  int MENU_LIMITED_NO=0;
    //限量
    public static final  int MENU_LIMITED_YES=1;


    /**
     * 套餐规格
     */
    //套餐规格：小份
    public static final int MENU_TYPE_SMALL=0;
    //套餐规格：默认
    public static final int MENU_TYPE_DEFAULT=1;
    //套餐规格：大份
    public static final int MENU_TYPE_BIG=2;

    /**
     * 套餐是否上架
     */
    //未上架
    public static final int MENU_UP_NO_ON_SALE=0;
    //已上架
    public static final int MENU_UP_ON_SALE=1;

    /**
     * 积分类型
     */
    //0 扣分
    public static final int SCORE_TYPE_DEDUCT =0;
    //1 加分
    public static final int SCORE_TYPE_ADD =1;
    //2 转换积分
    public static final int SCORE_TYPE_CHANGE =2;

    /**
     * 积分分类
     */
    //0 餐券
    public static final int SCORE_CLASSIFY_COUPON = 0;
    //1 积分
    public static final int SCORE_CLASSIFY_INTEGRAL = 1;




    /**
     * 上传文件基础虚拟路径
     */
    public static final String USERFILES_BASE_URL = "/userfiles/";

    /**
     * 获取当前对象实例
     */
    public static Global getInstance() {
        return global;
    }

    /**
     * 获取配置
     *
     * @see ${fns:getConfig('adminPath')}
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    /**
     * 获取管理端根路径
     */
    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    /**
     * 获取前端根路径
     */
    public static String getFrontPath() {
        return getConfig("frontPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    /**
     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
     */
    public static Boolean isDemoMode() {
        String dm = getConfig("demoMode");
        return "true".equals(dm) || "1".equals(dm);
    }

    /**
     * 在修改系统用户和角色时是否同步到Activiti
     */
    public static Boolean isSynActivitiIndetity() {
        String dm = getConfig("activiti.isSynActivitiIndetity");
        return "true".equals(dm) || "1".equals(dm);
    }

    /**
     * 页面获取常量
     *
     * @see ${fns:getConst('YES')}
     */
    public static Object getConst(String field) {
        try {
            return Global.class.getField(field).get(null);
        } catch (Exception e) {
            // 异常代表无配置，这里什么也不做
        }
        return null;
    }

    /**
     * 获取上传文件的根目录
     *
     * @return
     */
    public static String getUserfilesBaseDir() {
        String dir = getConfig("userfiles.basedir");
        if (StringUtils.isBlank(dir)) {
            try {
                dir = ServletContextFactory.getServletContext().getRealPath("/");
            } catch (Exception e) {
                return "";
            }
        }
        if (!dir.endsWith("/")) {
            dir += "/";
        }
//		System.out.println("userfiles.basedir: " + dir);
        return dir;
    }

    /**
     * 获取工程路径
     *
     * @return
     */
    public static String getProjectPath() {
        // 如果配置了工程路径，则直接返回，否则自动获取。
        String projectPath = Global.getConfig("projectPath");
        if (StringUtils.isNotBlank(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectPath;
    }

}
