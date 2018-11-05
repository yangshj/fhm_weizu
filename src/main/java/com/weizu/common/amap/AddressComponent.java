package com.weizu.common.amap;

import com.weizu.common.xml.IXMLNode;

import java.util.ArrayList;
import java.util.List;


public class AddressComponent {
	/** 坐标点所在省名称 */
	private String province;
	/** 坐标点所在城市名称 */
	private String city;
	/** 城市编码 */
	private String citycode;
	/** 坐标点所在区 */
	private String district;
	/** 行政区编码 */
	private String adcode;
	/** 坐标点所在乡镇 */
	private String township;
	/** 社区信息列表 */
	private Neighborhood neighborhood;
	/** 楼信息列表 */
	private Building building;
	/** 门牌信息列表 */
	private StreetNumber streetNumber;
	/** 所属海域信息 */
	private String seaArea;
	/** 经纬度所属商圈列表 */
	private List<BusinessArea> businessAreas;
	
	
	/////////////////////////////////////////////////////////////
	public AddressComponent(IXMLNode addressComponent){
		if(addressComponent==null || addressComponent.getChilds().size()==0) return;
		// 省份
		if(addressComponent.getChilds("province").size()>0){
			IXMLNode province = addressComponent.getChilds("province").get(0);
			if(province!=null){
				this.province = province.getText();
			}
		}
		// 地市
		if(addressComponent.getChilds("city").size()>0){
			IXMLNode city = addressComponent.getChilds("city").get(0);
			if(city!=null){
				this.city = city.getText();
			}
		}
		// 城市编码
		if(addressComponent.getChilds("citycode").size()>0){
			IXMLNode citycode = addressComponent.getChilds("citycode").get(0);
			if(citycode!=null){
				this.citycode = citycode.getText();
			}
		}
		// 坐标点所在区
		if(addressComponent.getChilds("district").size()>0){
			IXMLNode district = addressComponent.getChilds("district").get(0);
			if(district!=null){
				this.district = district.getText();
			}
		}
		// 行政区编码
		if(addressComponent.getChilds("adcode").size()>0){
			IXMLNode adcode = addressComponent.getChilds("adcode").get(0);
			if(adcode!=null){
				this.adcode = adcode.getText();
			}
		}
		// 坐标点所在乡镇
		if(addressComponent.getChilds("township").size()>0){
			IXMLNode township = addressComponent.getChilds("township").get(0);
			if(township!=null){
				this.township = township.getText();
			}
		}
		// 社区信息列表
		if(addressComponent.getChilds("neighborhood").size()>0){
			IXMLNode neighborhood = addressComponent.getChilds("neighborhood").get(0);
			if(neighborhood!=null){
				this.neighborhood = new Neighborhood(neighborhood);
			}
		}
		// 楼信息列表
		if(addressComponent.getChilds("building").size()>0){
			IXMLNode building = addressComponent.getChilds("building").get(0);
			if(building!=null){
				this.building = new Building(building);
			}
		}
		// 门牌信息列表
		if(addressComponent.getChilds("streetNumber").size()>0){
			IXMLNode streetNumber = addressComponent.getChilds("streetNumber").get(0);
			if(streetNumber!=null){
				this.streetNumber = new StreetNumber(streetNumber);
			}
		}
		// 所属海域信息
//		IXMLNode seaArea = addressComponent.getChilds("seaArea").get(0);
//		if(seaArea!=null){
//			this.seaArea = seaArea.getText();
//		}
		// 经纬度所属商圈列表
		if(addressComponent.getChilds("businessAreas")!=null && addressComponent.getChilds("businessAreas").size()>0){
			IXMLNode businessAreas = addressComponent.getChilds("businessAreas").get(0);
			List<IXMLNode> businessAreaList = businessAreas.getChilds("businessArea");
			if(businessAreaList!=null && businessAreaList.size()>=0){
				this.businessAreas = new ArrayList<BusinessArea>();
				for(IXMLNode businessArea : businessAreaList){
					this.businessAreas.add(new BusinessArea(businessArea));
				}
			}
		}
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAdcode() {
		return adcode;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	public String getTownship() {
		return township;
	}
	public void setTownship(String township) {
		this.township = township;
	}
	public Neighborhood getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(Neighborhood neighborhood) {
		this.neighborhood = neighborhood;
	}
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	public StreetNumber getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(StreetNumber streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getSeaArea() {
		return seaArea;
	}
	public void setSeaArea(String seaArea) {
		this.seaArea = seaArea;
	}
	public List<BusinessArea> getBusinessAreas() {
		return businessAreas;
	}
	public void setBusinessAreas(List<BusinessArea> businessAreas) {
		this.businessAreas = businessAreas;
	}
	
}
