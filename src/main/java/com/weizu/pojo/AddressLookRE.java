package com.weizu.pojo;

import java.util.List;

public class AddressLookRE {
	
	/** success/fail */
	private String result;
	/**  用户位置信息列表  */
	private List<AddressLookBean> listData;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<AddressLookBean> getListData() {
		return listData;
	}
	public void setListData(List<AddressLookBean> listData) {
		this.listData = listData;
	}
	
}
