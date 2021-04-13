/**
 * Copyright &copy; 2012-2014 <a href="http://www.dhc.com.cn">DHC</a> All rights reserved.
 */
package com.dhc.rad.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.dhc.rad.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 区域Entity
 * @author DHC
 * @version 2013-05-15
 */
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
//	private Area parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
//	private String name; 	// 区域名称
//	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

	private String areaLocation;


	private List<Area> subArea; //子区域集合 
	
	public Area(){
		super();
		this.sort = 30;
	}

	public Area(String id){
		super(id);
	}
	
//	@JsonBackReference
//	@NotNull
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}
//	
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}

	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
//
//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}


	public String getAreaLocation() {
		return areaLocation;
	}

	public void setAreaLocation(String areaLocation) {
		this.areaLocation = areaLocation;
	}

	@JsonIgnore
	public static String getRootId(){
		return "0";
	}
	
	@Override
	public String toString() {
		return name;
	}

	public List<Area> getSubArea() {
		if(subArea ==null){
			subArea = new ArrayList<Area>();
		}
		return subArea;
	}

	public void setSubArea(List<Area> subArea) {
		this.subArea = subArea;
	}
}