package com.weizu.common.amap;

import com.weizu.common.xml.IXMLDocument;
import com.weizu.common.xml.IXMLNode;
import com.weizu.common.xml.impl.XMLFactory;
import com.weizu.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 地理位置信息
 * @author yangshj
 */
public class GisInfo implements Serializable{
	
	private static final long serialVersionUID = -6734494777427903194L;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
	/** 结构化地址信息 */
	private String formatted_address;
	/** 地址元素列表 */
	private AddressComponent addressComponent;
	/** 兴趣点 */
	private List<Poi> pois;
	/** 道路信息列表 */
	private List<Road> roads;
	/** 道路交叉口列表 */
	private List<Roadinter> roadinters;
	/** 位置详情 */
	private String content; 
	/** 经度 */
	private double longitude;
	/** 纬度 */
	private double Latitude;
	/** 返回原始xml */
	private String xmlStr;

	public GisInfo() {}
	public GisInfo(double longitude2, double latitude2) {
		this.Latitude = latitude2;
		this.longitude = longitude2;
		doRequest();
		// 处理不详，附近情况, 再调一次
		for(int i=0; i<3; i++){
			if(content==null || content.length()<3 || content.contains("不详")){
				System.out.println("content: "+content);
				doRequest();
				System.out.println("第"+i+"次请求"+"content: "+content);
			} else {
				break;
			}
		}
	}
	protected void doRequest() {
		if(xmlStr==null){
			try {
				xmlStr = request(getUrl());
				if(StringUtils.isEmpty(xmlStr, true)){
					System.out.println(sdf.format(new Date())+":\t\t高德返回null位置");
					return;
				}
			} catch (Exception e) {
				System.out.println(sdf.format(new Date())+":\t\t高德请求超时位置");
				if(StringUtils.isEmpty(xmlStr, true)){
					return;
				}
			}
		}
		parseXml(xmlStr);
	}

	public String getXmlStr() {
		return xmlStr;
	}
	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}
	private void parseXml(String xml) {
		// 声明变量
		String content = "";
		if(null != xml && !xml.isEmpty()){
			IXMLDocument doc = XMLFactory.getByText(xml);
			// 根节点
			if (doc != null) {
				IXMLNode root = doc.getRootNode();
				String rootName = root.getNodeName();
				if (rootName.equals("response")) {
					// 返回结果状态值，值为 0 或 1,0 表示 false；1 表示 true
					IXMLNode status = root.getChilds("status").get(0);
					if(status.getText().equals("1")) {
						// 全局节点
						IXMLNode regeocode = root.getChilds("regeocode").get(0);
						// 结构化地址信息
						IXMLNode formatted_address = regeocode.getChilds("formatted_address").get(0);
						this.formatted_address = formatted_address.getText();
						content = formatted_address.getText();
						// 地址元素列表
						IXMLNode addressComponent = regeocode.getChilds("addressComponent").get(0);
						this.addressComponent = new AddressComponent(addressComponent);
						// 兴趣点
						if(regeocode.getChilds("pois").size()>0){
							IXMLNode pois = regeocode.getChilds("pois").get(0);
							List<IXMLNode> poisList = pois.getChilds("poi");
							if(poisList!=null && poisList.size()>0){
								this.pois = new ArrayList<Poi>();
								for(IXMLNode poi : poisList){
									this.pois.add(new Poi(poi));
								}
							}
						}
						// 道路信息列表
						if(regeocode.getChilds("roads").size()>0){
							IXMLNode roads = regeocode.getChilds("roads").get(0);
							List<IXMLNode> roadList = roads.getChilds("road");
							if(roadList!=null && roadList.size()>0){
								this.roads = new ArrayList<Road>();
								for(IXMLNode road : roadList){
									this.roads.add(new Road(road));
								}
							}
						}
						// 道路交叉口列表
						if(regeocode.getChilds("roadinters").size()>0){
							IXMLNode roadinters = regeocode.getChilds("roadinters").get(0);
							List<IXMLNode> roadinteList = roadinters.getChilds("roadinter");
							if(roadinteList!=null && roadinteList.size()>0){
								this.roadinters = new ArrayList<Roadinter>();
								for(IXMLNode roadinte : roadinteList){
									this.roadinters.add(new Roadinter(roadinte));
								}
							}
						}
					} else {
						content = root.getChilds("info").get(0).getText();
					}
				} else {
					
				}
			} else {
				content = "不详";
			}
		} else {
			content = "不详";
		}
		this.content = content;
	}

	static String request(String strUrl) throws MalformedURLException, IOException {
		String responseMessageXml;
		StringBuilder result = new StringBuilder();
		URL serviceurl = new URL(strUrl);
		URLConnection connection = serviceurl.openConnection();
		connection.setReadTimeout(10000);
		connection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream(),"UTF-8"));
		String line;
		while ((line = in.readLine()) != null) {
			result.append(line);
			result.append("\n");
		}
		in.close();
		responseMessageXml = result.toString();
		return responseMessageXml;
	}

	private String getUrl() {
		String strUrl = "http://restapi.amap.com/v3/geocode/regeo?location=" + longitude + "," + Latitude
				+ "&extensions=all&output=xml&roadlevel=1&key=5b9d0b1369270d96f46f541f273f4bff";
		System.out.println("strUrl: "+strUrl);
		return strUrl;
	}

	
	
	/////////////////////////////////////////////////////////////////////////////
	
	public List<Poi> getPois() {
		return pois;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}
	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}
	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}
	public List<Road> getRoads() {
		return roads;
	}
	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}
	public List<Roadinter> getRoadinters() {
		return roadinters;
	}
	public void setRoadinters(List<Roadinter> roadinters) {
		this.roadinters = roadinters;
	}
	public String getContent() {
		if(content.contains("不详")) return this.content;
		String content = this.content;
		if(addressComponent!=null && addressComponent.getStreetNumber()!=null){
			if(!content.contains( addressComponent.getStreetNumber().getStreet())){
				if(addressComponent.getStreetNumber().getStreet()!=null){
					content += addressComponent.getStreetNumber().getStreet();
					content += addressComponent.getStreetNumber().getNumber();
					if(!content.contains("号")){
						content += "号";
					}
				}
			}
		}
		if(getRoads()!=null && getRoads().size()>0){
			content += (", 附近道路:"+getRoads().get(0).getName());
		}
		if(getRoadinters()!=null && getRoadinters().size()>0){
			content += (", 附近交叉口:"+getRoadinters().get(0).getFirst_name()+"与"+getRoadinters().get(0).getSecond_name()+"交叉口");
		}
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	///////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		GisInfo info = new GisInfo(116.496824,39.783641);
		String content = info.getContent();
		System.out.println(content);
	}
}
