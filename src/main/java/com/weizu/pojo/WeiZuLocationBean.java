package com.weizu.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;


@Alias("weiZuLocationBean")
public class WeiZuLocationBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 用户id */
	private Long userId;
	/** 纬度 */
	private Double latitude;
	/**  精度  */
	private Double  longitude ;
	/** 位置信息描述  */
	private String locationInfo;
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}
	
}
