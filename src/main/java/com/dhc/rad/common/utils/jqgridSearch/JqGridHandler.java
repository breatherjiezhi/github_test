/**      
 * @文件名称: JqGridHandler.java  
 * @类路径: com.dhc.rad.common.utils.jqgridSearch  
 * @描述: TODO  
 * @作者：fangzr   
 * @时间：2015-11-3 下午02:48:31  
 * @版本：V1.0     
 */
package com.dhc.rad.common.utils.jqgridSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import com.dhc.rad.common.mapper.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：DHC
 * @作者：fangzr
 * @创建时间：2015-11-3 下午02:48:31
 * @版本：V1.0
 */
public class JqGridHandler {
	private HttpServletRequest request = null;

	private String _search = "false";
	private String searchField;
	private String searchOper;
	private String searchString;
	private String filters;

	// 存储总体的search
	FilterSearch filterSearch = null;

	public JqGridHandler() {

	}

	public JqGridHandler(HttpServletRequest request) {
		this.request = request;

	}

	/***
	 * 函数功能说明 :
	 * 作者: fangzr  
	 * 创建时间：2015-11-3
	 * 修改者名字:
	 * 修改日期 :
	 * 修改内容 :
	 * @参数： @param prefix
	 * 					前缀是指查询条件在SQL中是否对应的表是否有别名，一般情况取""或者NULL;
	 * @参数： @param isWhere
	 * 					是否在其他查询条件之后拼查询条件，如果是这取值true（and 1=1），如果没有where条件取值false（and 1=1）
	 * @参数： @return
	 * @throws
	 */
	public String getWheres(String prefix, boolean isWhere) {
		conditions();
		if (tranToSQL(prefix).trim().equals("")) {
			return "";
		}
		if (!isWhere) {
			return new StringBuilder(" where ").append(tranToSQL(prefix))
					.toString();
		}
		return new StringBuilder(" and ").append(tranToSQL(prefix)).toString();
	}



	// 根据conditions转换成sql格式
	public String tranToSQL(String prefix) {
		StringBuilder sb = new StringBuilder("");

		if (null != filterSearch) {
			List<SearchRule> rules = filterSearch.getRules();
			int count = 0;
			if (null != rules && (count = rules.size()) > 0) {
				for (SearchRule rule : rules) {
					if (null != rule.getField() && null != rule.getData()
							&& null != rule.getOp()) {
						if ("eq".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" = ")
									.append("'").append(rule.getData())
									.append("'");

						} else if ("ne".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" <> ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("lt".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" < ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("le".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" <= ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("gt".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" > ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("ge".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" >= ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("bw".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append(rule.getData())
									.append("%").append("'");
						} else if ("bn".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" not like ")
									.append("'").append(rule.getData())
									.append("%").append("'");
						} else if ("ew".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append("%")
									.append(rule.getData()).append("'");
						} else if ("en".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" not like ")
									.append("'").append("%")
									.append(rule.getData()).append("'");
						} else if ("cn".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" like ")
									.append("'").append("%")
									.append(rule.getData()).append("%")
									.append("'");
						} else if ("nu".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" is null ")
									.append("'").append(rule.getData())
									.append("'");
						} else if ("nn".equalsIgnoreCase(rule.getOp())) {
							if (null != prefix && "".equals(prefix)) {
								sb.append(prefix).append(".");
							}
							sb.append(rule.getField()).append(" is not null ")
									.append("'").append(rule.getData())
									.append("'");
						} else {

						}
						count--;
						if (count > 0) {
							if (null != filterSearch.getGroupOp()) {
								if (filterSearch.getGroupOp().equals("and"))
									sb.append(" and ");
								else
									sb.append(" or ");
							}

						}
					}

				}
			}
		}
		return sb.toString();
	}

	// 装载

	private void conditions() {
		// 初始化，如果request为空，说明是从set进来的。
		init();

		// 分拆，全部写入filersearch
		if (null != _search && "true".equalsIgnoreCase(_search)) {
			// 先写多选择的，一般有多选择就不会有单选择。
			if (null != filters && filters.length() > 0) {
//				Map m = new HashMap();
//				m.put("rules", SearchRule.class);
				filterSearch = (FilterSearch)JsonMapper.fromJsonString(filters, FilterSearch.class);
			} else {
				if (null != searchOper && null != searchString
						&& null != searchField) {
					SearchRule rule = new SearchRule();
					rule.setData(searchString);
					rule.setOp(searchOper);
					rule.setField(fiterSearchField(doTables(searchField)));
					filterSearch = new FilterSearch();
					filterSearch.setGroupOp(null);
					List<SearchRule> rules = new ArrayList<SearchRule>();
					rules.add(rule);
					filterSearch.setRules(rules);
				}
			}
		}

	}

	private void init() {
		if (request != null) {
			_search = request.getParameter("_search");
			searchOper = request.getParameter("searchOper");
			searchString = request.getParameter("searchString");
			searchField = request.getParameter("searchField");
			filters = request.getParameter("filters");
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String get_search() {
		return _search;
	}

	public void set_search(String _search) {
		this._search = _search;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public FilterSearch getFilterSearch() {
		return filterSearch;
	}

	public void setFilterSearch(FilterSearch filterSearch) {
		this.filterSearch = filterSearch;
	}

	private String doTables(String str) {
		if (str.startsWith("__")) {
			str = str.substring(2);
			return str.replaceAll("_", ".");
		} else {
			return str;
		}

	}
	
	
	/**
	 * 获取查询排序字符串
	 * @return
	 */
	@JsonIgnore
	public String fiterSearchField(String str) {
		// SQL过滤，防止注入 
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
					+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		if (sqlPattern.matcher(str).find()) {
			return "";
		}
		return str;
	}
}
