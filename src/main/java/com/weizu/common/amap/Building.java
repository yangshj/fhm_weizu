package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

public class Building {

	/** 建筑名称 */
	private String name;
	/** 类型 */
	private String type;
	
	//////////////////////////
	public Building(IXMLNode building) {
		if(building.getChilds().size()==0) return;
		// 社区名称
		if(building.getChilds("name").size()>0){
			IXMLNode name = building.getChilds("name").get(0);
			if(name!=null){
				this.name = name.getText();
			}
		}
		// 类型
		if(building.getChilds("type").size()>0){
			IXMLNode type = building.getChilds("type").get(0);
			if(type!=null){
				this.type = type.getText();
			}
		}
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
	
}
