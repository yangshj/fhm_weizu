package com.weizu.pojo.addressBook;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

public class AddressLookRE extends BaseRE{
	
	/** success/fail */
	private String result;
	/** 是否有管理员权限 */
	private Boolean managerRights = false;
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
	public Boolean getManagerRights() {
		return managerRights;
	}
	public void setManagerRights(Boolean managerRights) {
		this.managerRights = managerRights;
	}
}
