package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * 道路信息列表，请求参数 extensions=all 时返回；extensions=base 时不返回
 * @author yangshj
 *
 */
public class Road {
	
	/** 道路 id */
	private String id;
	/** 道路名称 */
	private String name;
	/** 道路到请求坐标的距离，单位：米 */
	private String distance;
	/** 方位 */
	private String direction;
	/** 坐标点 */
	private String location;
	
	
	//////////////////////////////////
	public Road(IXMLNode road) {
		if(road.getChilds().size()==0) return;
		// 道路 id
		if(road.getChilds("id").size()>0){
			IXMLNode id = road.getChilds("id").get(0);
			if(id!=null){
				this.id = id.getText();
			}
		}
		// 道路名称
		if(road.getChilds("name").size()>0){
			IXMLNode name = road.getChilds("name").get(0);
			if(name!=null){
				this.name = name.getText();
			}
		}
		// 道路到请求坐标的距离，单位：米
		if(road.getChilds("distance").size()>0){
			IXMLNode distance = road.getChilds("distance").get(0);
			if(distance!=null){
				this.distance = distance.getText();
			}
		}
		// 方位
		if(road.getChilds("direction").size()>0){
			IXMLNode direction = road.getChilds("direction").get(0);
			if(direction!=null){
				this.direction = direction.getText();
			}
		}
		// 坐标点
		if(road.getChilds("location").size()>0){
			IXMLNode location = road.getChilds("location").get(0);
			if(location!=null){
				this.location = location.getText();
			}
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
