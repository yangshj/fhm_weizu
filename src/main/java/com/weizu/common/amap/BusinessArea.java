package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * 经纬度所属商圈列表
 * @author yangshj
 *
 */
public class BusinessArea {
	
	/** 商圈中心点经纬度 */
	private String location;
	/** 商圈名称 */
	private String name;
	/** 商圈 id */
	private String id;
	
	
	//////////////////////////////////////
	public BusinessArea(IXMLNode businessArea) {
		if(businessArea.getChilds().size()==0) return;
		// 商圈中心点经纬度
		if(businessArea.getChilds("location").size()>0){
			IXMLNode location = businessArea.getChilds("location").get(0);
			if(location!=null){
				this.location = location.getText();
			}
		}
		// 商圈名称
		if(businessArea.getChilds("name").size()>0){
			IXMLNode name = businessArea.getChilds("name").get(0);
			if(name!=null){
				this.name = name.getText();
			}
		}
		// 商圈 id
		if(businessArea.getChilds("id").size()>0){
			IXMLNode id = businessArea.getChilds("id").get(0);
			if(id!=null){
				this.id = id.getText();
			}
		}
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
