/**      
 * @文件名称: FilterSearch.java  
 * @类路径: com.dhc.rad.common.utils.jqgridSearch  
 * @描述: TODO  
 * @作者：fangzr   
 * @时间：2015-11-3 下午02:45:59  
 * @版本：V1.0     
 */
package com.dhc.rad.common.utils.jqgridSearch;

import java.util.List;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：DHC
 * @作者：fangzr
 * @创建时间：2015-11-3 下午02:45:59
 * @版本：V1.0
 */
class FilterSearch {
	
	private String groupOp; // 多字段查询时分组类型，主要是AND或者OR

	private List<SearchRule> rules; // 多字段查询时候，查询条件的集合

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<SearchRule> getRules() {
		return rules;
	}

	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}
}
