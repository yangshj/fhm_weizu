package com.weizu.pojo;

import java.util.List;

/**
 * 用户定位信息--用于地图展示
 * @author yangshj
 *
 */
public class UserLocationRe {
	
	/** success/fail */
	private String result;
	/**  用户位置信息列表  */
	private List<UserLocationMarkerBean> markers;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<UserLocationMarkerBean> getMarkers() {
		return markers;
	}
	public void setMarkers(List<UserLocationMarkerBean> markers) {
		this.markers = markers;
	}
	
	
}
