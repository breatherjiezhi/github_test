package com.dhc.rad.test.entity;

public class TestGsonBean {
	
	
	private String  CityId;
	private String  CityName;
	private String  ProvinceId;	
	private String  CityOrder;
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(String provinceId) {
		ProvinceId = provinceId;
	}
	public String getCityOrder() {
		return CityOrder;
	}
	public void setCityOrder(String cityOrder) {
		CityOrder = cityOrder;
	}
	
	
	@Override
	public String toString() {
		return "TestGsonBean [CityId=" + CityId + ", CityName=" + CityName + ", ProvinceId=" + ProvinceId
				+ ", CityOrder=" + CityOrder + "]";
	}
	
	
	
}
