package com.dhc.rad.common.utils;

import java.util.Date;
import java.util.UUID;

/*
 * 
 * @author hj
 *
 * 计划编号生成工具
 */
public class PlanCodeUtils {


	/**
	 * @param prefix 生成一个计划编号。
	 * @return
	 */
	public static String planCodeGen(String prefix) {
		String formatePattern = "yyMMddHHmmss";
		String dateTimeStr = DateUtils.formatDate(new Date(), formatePattern);
		StringBuffer sb = new StringBuffer(prefix);
		/*sb.append('-');
		sb.append('-');
		sb.append(IdGen.randomBase62(6));*/
		sb.append(dateTimeStr);
		return sb.toString();
	}

	/**
	 * @param prefix 生成一个流水号。
	 * 例如：SS-20160819153526
	 * @return
	 */
	public static String createCodeGen(String prefix) {
		String formatePattern = "yyyyMMddHHmmssSSS";
		String dateTimeStr = DateUtils.formatDate(new Date(), formatePattern);
		StringBuffer sb = new StringBuffer(prefix);
		sb.append('-');
		sb.append(dateTimeStr);
		return sb.toString();
	}
	
	
	/**
	 * 生成一个流水号
	 * 例如：20160819153526
	 * @return
	 */
	public static String createCodeGen() {
		String formatePattern = "yyyyMMdd-HHmmssSSS";
		String dateTimeStr = DateUtils.formatDate(new Date(), formatePattern);
		StringBuffer sb = new StringBuffer();
		sb.append(dateTimeStr);
		return sb.toString();
	}
	
	
	/**
	 * 生成一个uuid流水号
	 * 例如：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12);
	 * @return
	 */
	public static String createUUID() {
		
//		String uuid = UUID.randomUUID().toString();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		return uuid;
	}
	
	
	

	public static void main(String[] args) {
		
		System.out.println(createCodeGen());
	}
}
