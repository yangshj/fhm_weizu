package com.weizu.common.amap;


import com.weizu.common.xml.IXMLNode;

/**
 * 社区信息列表
 * @author yangshj
 *
 */
public class Neighborhood {
	
	/** 社区名称 */
	private String name;
	/** 类型 */
	private String type;
	
	//////////////////////////
	public Neighborhood(IXMLNode neighborhood) {
		if(neighborhood.getChilds().size()==0) return;
		// 社区名称
		if(neighborhood.getChilds("name").size()>0){
			IXMLNode name = neighborhood.getChilds("name").get(0);
			if(name!=null){
				this.name = name.getText();
			}
		}
		// 类型
		if(neighborhood.getChilds("type").size()>0){
			IXMLNode type = neighborhood.getChilds("type").get(0);
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
