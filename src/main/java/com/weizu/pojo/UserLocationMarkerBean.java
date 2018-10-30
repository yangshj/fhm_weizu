package com.weizu.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 用户定位信息--用于地图展示
 * @author yangshj
 *
 */
@Alias("userLocationBeanMarker")
public class UserLocationMarkerBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** id */
	private Long id;
	/** 纬度 */
	private Double latitude;
	/**  精度  */
	private Double  longitude;
	/** 用户昵称 */
    private String name;
    /** 用户头像 */
    private String iconPath;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
    
}
