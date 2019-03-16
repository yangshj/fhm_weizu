package com.weizu.pojo.addressBook;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

/**
 * 用户定位信息--用于地图展示
 * @author yangshj
 *
 */
public class UserLocationRe extends BaseRE{
	

	/**  用户位置信息列表  */
	private List<UserLocationMarkerBean> markers;
	

	public List<UserLocationMarkerBean> getMarkers() {
		return markers;
	}
	public void setMarkers(List<UserLocationMarkerBean> markers) {
		this.markers = markers;
	}
	
	
}
