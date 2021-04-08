package com.dhc.rad.common.config;

import com.ckfinder.connector.ServletContextFactory;
import com.dhc.rad.common.utils.PropertiesLoader;
import com.dhc.rad.common.utils.StringUtils;
import com.google.common.collect.Maps;
import org.apache.ibatis.thread.PropertiesUtil;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * webservice配置类
 * @author hj
 * @version 2018-11-14
 */
public class Webservice {

	private static String filename = "webservice.properties";


	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		// 重新加载配置文件
		PropertiesLoader loader = new PropertiesLoader(filename);
		String value = "";
		if (key != null){
			value = loader.getProperty(key);
		}
		return value;
	}

}
