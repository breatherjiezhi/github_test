package com.dhc.rad.common.utils;

/**
 * 
 * @author hejun
 * 公用的一些验证方法
 *
 */

public class ValidateUtils {

	/**
	 * @param aoValidity AO有效 
	 * @param sortie 分配架次
	 * @return isOk
	 */
	public static boolean checkSortie(String sortie,String aoValidity) {
		boolean isOk = false;
		String[] sortieArray = aoValidity.split(",");
		for(String sorties: sortieArray){
//			System.out.println(sorties);
			String[] array = sorties.trim().split("-");
//			System.out.println(array[0] +"   "+ array[1]);	
		
			if(Integer.parseInt(array[0])<= Integer.parseInt(sortie) && Integer.parseInt(array[array.length-1])>= Integer.parseInt(sortie)){
				isOk = true;
			}
//			System.out.println(isOk);
		}

		return isOk;
	}
	

	
	// main 方法调用验证
	public static void main(String[] args) {
		
		checkSortie("001-012,013-033,056-188","133");
	}

}
