/**      
  * @文件名称: CodeRuleUtils.java  
  * @类路径: com.dhc.rad.common.utils  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-2 下午02:46:53  
  * @版本：V1.0     
  */  
package com.dhc.rad.common.utils.code;


/**
 * @类功能说明： 生成业务编码流水号
 * @类修改者： 
 * @修改日期： 
 * @修改说明：
 */
public class CodeRuleUtils {

	//物料单
	public static String MATERIEL="WL";

	//订单流水号
	public static String ORDERS="SQ";


	//供货单
	public static String PURCHASE = "PO";
	
	//发货单
	public static String INVOICE = "FH";

	//入库单
	public static String ENWARE = "RK";
	
	//装箱单
	public static String PACKAGE = "ZX";
	
	//出库单
	public static String EXWARE = "CK";

	//领料单
	public static String REQUISITION = "LL";

	//中心材料库出库单
	public static String CENWARE = "RKD";


	//中心材料库出库单
	public static String CEXWARE = "CC";

    //中心材料库调拨单
    public static String ADJUST = "AD";

    //中心材料库调拨单明细批次号
	public static String BATCHCODE = "ADDE";

	//中心材料库形态转换单
	public static String CSHIFT = "ZH";

	public static String BACK = "TL";
	//任务号
	public static String trid = "T";

}