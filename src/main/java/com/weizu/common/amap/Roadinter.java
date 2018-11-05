package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * 道路交叉口列表，请求参数 extensions=all 时返回；extensions=base 时不返回
 * @author yangshj
 *
 */
public class Roadinter {
	
	/** 交叉路口到请求坐标的距离，单位：米 */
	private String distance;
	/** 方位 */
	private String direction;
	/** 路口经纬度 */
	private String location;
	/** 第一条道路 id */
	private String first_id;
	/** 第一条道路名称 */
	private String first_name;
	/** 第二条道路 id */
	private String second_id;
	/** 第二条道路名称 */
	private String second_name;
	
	
	///////////////////////////////////////////////////////
	public Roadinter(IXMLNode roadinter) {
		if(roadinter.getChilds().size()==0) return;
		// 交叉路口到请求坐标的距离，单位：米
		if(roadinter.getChilds("distance").size()>0){
			IXMLNode distance = roadinter.getChilds("distance").get(0);
			if(distance!=null){
				this.distance = distance.getText();
			}
		}
		// 方位
		if(roadinter.getChilds("direction").size()>0){
			IXMLNode direction = roadinter.getChilds("direction").get(0);
			if(direction!=null){
				this.direction = direction.getText();
			}
		}
		// 路口经纬度
		if(roadinter.getChilds("location").size()>0){
			IXMLNode location = roadinter.getChilds("location").get(0);
			if(location!=null){
				this.location = location.getText();
			}
		}
		// 第一条道路 id
		if(roadinter.getChilds("first_id").size()>0){
			IXMLNode first_id = roadinter.getChilds("first_id").get(0);
			if(first_id!=null){
				this.first_id = first_id.getText();
			}
		}
		// 第一条道路名称
		if(roadinter.getChilds("first_name").size()>0){
			IXMLNode first_name = roadinter.getChilds("first_name").get(0);
			if(first_name!=null){
				this.first_name = first_name.getText();
			}
		}
		// 第二条道路 id
		if(roadinter.getChilds("second_id").size()>0){
			IXMLNode second_id = roadinter.getChilds("second_id").get(0);
			if(second_id!=null){
				this.second_id = second_id.getText();
			}
		}
		// 第二条道路名称
		if(roadinter.getChilds("second_name").size()>0){
			IXMLNode second_name = roadinter.getChilds("second_name").get(0);
			if(second_name!=null){
				this.second_name = second_name.getText();
			}
		}
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFirst_id() {
		return first_id;
	}
	public void setFirst_id(String first_id) {
		this.first_id = first_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getSecond_id() {
		return second_id;
	}
	public void setSecond_id(String second_id) {
		this.second_id = second_id;
	}
	public String getSecond_name() {
		return second_name;
	}
	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}
	
}
