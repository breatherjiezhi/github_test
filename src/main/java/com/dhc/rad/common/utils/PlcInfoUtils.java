/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.common.utils;

import java.util.HashMap;
import java.util.Map;


public class PlcInfoUtils {

	private static Map<String, String> plcList = new HashMap<String, String>();

	private static Map<String, Integer> writePlcList = new HashMap<String, Integer>();

	public static void add(String addr,String values){
		plcList.put(addr,values);
	}

	public static void updWriteInfo(String addr,Integer values){
		writePlcList.put(addr,values);
	}

	public static Map<String, Integer> getWritePlcList() {
		return writePlcList;
	}

	public static Map<String, String> getPlcList() {
		return plcList;
	}


	//异常信息停止标记--key:wareKey;轴加工--key:shaftKey;壳加工--key:capsidKey;分油盘加工--key:discKey;刀具出--knifeoutKey;刀具入--knifeinKey;限制校验RFID--checkrfid; 限制料到位接口--instockcontroller;限制AGV到位接口--agvarrivedfinish
	private static Map<String, Integer> ware = new HashMap<String, Integer>();
	//更新 wareKey 的值
	public static Map<String, Integer> updateWare(String addr,int values){
		ware.put(addr,values);
		return ware;
	}

	public static int getWareKey() {
		if(ware.containsKey("wareKey")==false){
			ware.put("wareKey",0);

		}
		int t=ware.get("wareKey");
		return t;
	}

	public static int getShaftKey() {
		if(ware.containsKey("shaftKey")==false){
			ware.put("shaftKey",0);

		}
		int t=ware.get("shaftKey");
		return t;
	}
	public static int getCapsidKey() {
		if(ware.containsKey("capsidKey")==false){
			ware.put("capsidKey",0);

		}
		int t=ware.get("capsidKey");
		return t;
	}
	public static int getDiscKey() {
		if(ware.containsKey("discKey")==false){
			ware.put("discKey",0);
		}
		int t=ware.get("discKey");
		return t;
	}
	public static int getKnifeoutKey() {
		if(ware.containsKey("knifeoutKey")==false){
			ware.put("knifeoutKey",0);
		}
		int t=ware.get("knifeoutKey");
		return t;
	}
	public static int getKnifeinKey() {
		if(ware.containsKey("knifeinKey")==false){
			ware.put("knifeinKey",0);

		}
		int t=ware.get("knifeinKey");
		return t;
	}

	public static int getCheckRfid() {
		if(ware.containsKey("checkrfid")==false){
			ware.put("checkrfid",1);

		}
		int t=ware.get("checkrfid");
		return t;
	}
	public static int getInstockController() {
		if(ware.containsKey("instockcontroller")==false){
			ware.put("instockcontroller",1);
		}
		int t=ware.get("instockcontroller");
		return t;
	}
	public static int getAgvArrivedFinish() {
		if(ware.containsKey("agvarrivedfinish")==false){
			ware.put("agvarrivedfinish",1);
		}
		int t=ware.get("agvarrivedfinish");
		return t;
	}

}
