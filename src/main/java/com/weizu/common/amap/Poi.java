package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * poi 信息列表，请求参数 extensions=all 时返回；extensions=base 时不返
 * @author yangshj
 *
 */
public class Poi {
	
	/** 兴趣点 id */
	private String id;
	/** 兴趣点名称 */
	private String name;
	/** 兴趣点类型 */
	private String type;
	/** 电话 */
	private String tel;
	/** 该 POI 到请求坐标的距离，单位：米 */
	private String distance;
	/** 方向 */
	private String direction;
	/** poi 地址信息 */
	private String address;
	/** poi 权重 */
	private String poiweight;
	/** 坐标点 */
	private String location;
	/** poi 所在商圈名称 */
	private String businessarea;
	
	
	/////////////////////////////////////////////////////
	public Poi(IXMLNode poi) {
		if(poi.getChilds().size()==0) return;
		// 兴趣点 id
		if(poi.getChilds("id").size()>0){
			IXMLNode id = poi.getChilds("id").get(0);
			if(id!=null){
				this.id = id.getText();
			}
		}
		// 兴趣点名称
		if(poi.getChilds("name").size()>0){
			IXMLNode name = poi.getChilds("name").get(0);
			if(name!=null){
				this.name = name.getText();
			}
		}
		// 兴趣点类型
		if(poi.getChilds("type").size()>0){
			IXMLNode type = poi.getChilds("type").get(0);
			if(type!=null){
				this.type = type.getText();
			}
		}
		// 电话
		if(poi.getChilds("tel").size()>0){
			IXMLNode tel = poi.getChilds("tel").get(0);
			if(tel!=null){
				this.tel = tel.getText();
			}
		}
		// 该 POI 到请求坐标的距离，单位：米
		if(poi.getChilds("distance").size()>0){
			IXMLNode distance = poi.getChilds("distance").get(0);
			if(distance!=null){
				this.distance = distance.getText();
			}
		}
		// 方向
		if(poi.getChilds("direction").size()>0){
			IXMLNode direction = poi.getChilds("direction").get(0);
			if(direction!=null){
				this.direction = direction.getText();
			}
		}
		// poi 地址信息
		if(poi.getChilds("address").size()>0){
			IXMLNode address = poi.getChilds("address").get(0);
			if(address!=null){
				this.address = address.getText();
			}
		}
		// poi 权重
		if(poi.getChilds("poiweight").size()>0){
			IXMLNode poiweight = poi.getChilds("poiweight").get(0);
			if(poiweight!=null){
				this.poiweight = poiweight.getText();
			}
		}
		// 坐标点
		if(poi.getChilds("location").size()>0){
			IXMLNode location = poi.getChilds("location").get(0);
			if(location!=null){
				this.location = location.getText();
			}
		}
		// poi 所在商圈名称
		if(poi.getChilds("businessarea").size()>0){
			IXMLNode businessarea = poi.getChilds("businessarea").get(0);
			if(businessarea!=null){
				this.businessarea = businessarea.getText();
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPoiweight() {
		return poiweight;
	}
	public void setPoiweight(String poiweight) {
		this.poiweight = poiweight;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBusinessarea() {
		return businessarea;
	}
	public void setBusinessarea(String businessarea) {
		this.businessarea = businessarea;
	}
	
	
}
