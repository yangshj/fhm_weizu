package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * 门牌信息列表
 * @author yangshj
 *
 */
public class StreetNumber {
	
	/** 街道名称 */
	private String street;
	/** 门牌号 */
	private String number;
	/** 坐标点 */
	private String location;
	/** 方向 */
	private String direction;
	/** 门牌地址到请求坐标的距离，单位：米 */
	private String distance;
	
	
	////////////////////////////////////////////////////////
	public StreetNumber(IXMLNode streetNumber) {
		if(streetNumber.getChilds().size()==0) return;
		// 街道名称
		if(streetNumber.getChilds("street").size()>0){
			IXMLNode street = streetNumber.getChilds("street").get(0);
			if(street!=null){
				this.street = street.getText();
			}
		}
		// 门牌号
		if(streetNumber.getChilds("number").size()>0) {
			IXMLNode number = streetNumber.getChilds("number").get(0);
			if(number!=null){
				this.number = number.getText();
			}
		}
		// 坐标点
		if(streetNumber.getChilds("location").size()>0){
			IXMLNode location = streetNumber.getChilds("location").get(0);
			if(location!=null){
				this.location = location.getText();
			}
		}
		// 方向
		if(streetNumber.getChilds("direction").size()>0){
			IXMLNode direction = streetNumber.getChilds("direction").get(0);
			if(direction!=null){
				this.direction = direction.getText();
			}
		}
		// 门牌地址到请求坐标的距离，单位：米
		if(streetNumber.getChilds("distance1").size()>0){
			IXMLNode distance = streetNumber.getChilds("distance").get(0);
			if(distance!=null){
				this.distance = distance.getText();
			}
		}
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
}
