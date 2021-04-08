/**      
 * @文件名称: SearchRule.java  
 * @类路径: com.dhc.rad.common.utils.jqgridSearch  
 * @描述: TODO  
 * @作者：fangzr   
 * @时间：2015-11-3 下午02:44:46  
 * @版本：V1.0     
 */
package com.dhc.rad.common.utils.jqgridSearch;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：DHC
 * @作者：fangzr
 * @创建时间：2015-11-3 下午02:44:46
 * @版本：V1.0
 */
class SearchRule {
	
	private String field; // 查询字段
	private String op; // 查询操作
	private String data; // 选择的查询值
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}


}
