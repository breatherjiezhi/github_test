package com.dhc.rad.common.utils.convertUtils;


import java.io.File;
import java.net.URISyntaxException;


public class PathUtil {

	/**
	 * @return ��ȡweb-infoĿ¼
	 */
	public static String getWEBINFDir() {
		String path = null;
		try {
			path = PathUtil.class.getResource("").toURI().getPath();
			path = path.substring(0, path.indexOf("classes"));
			return path;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return WebRootĿ¼�ľ���·��
	 */
	public static String getWebRootDir() {
		String path = null;
		String folderPath = PathUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0, folderPath
					.indexOf("WEB-INF/classes"));
		}
		path = path.replaceAll("%20", " ");
		return path;
	}
	
	/**
	 * @param args
	 */
	public static String getWebRootDirFilePath(String dir){
		String path = getWebRootDir()  + dir;
		File file = new File(path);
		if(! file.exists()){
			file.mkdirs();
		}
		return path;
	}
	
	/**
	 * @param args
	 */
	public static String getWebInfoDirFilePath(String dir){
		String path = getWEBINFDir()  + dir;
		File file = new File(path);
		if(! file.exists()){
			file.mkdirs();
		}
		return path;
	}
	
}
