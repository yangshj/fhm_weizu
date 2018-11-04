package com.weizu.service;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.AddressLookBean;

public interface AddressLookService {
	
	/** 查找通讯录 */
	AddressLookBean findAddressLookById(AddressLookBean bean) throws Exception;

	List<AddressLookBean> findAddressLookByCondition(AddressLookBean bean) throws Exception;
	
	/** 插入通讯录 */
	Integer inserAddressLook(AddressLookBean bean) throws Exception;
	
	/** 修改通讯录 */
	Integer updateAddressLook(AddressLookBean bean) throws Exception;
	
	/** 删除通讯录 */
	void deleteAddressLook(AddressLookBean bean) throws Exception;
	
	/** 获取所有通讯录信息 */
	List<AddressLookBean> getAllAddressLook() throws Exception;
	
	/** 获取所有通讯录信息 */
	List<PageData> getAllAddressLookListPage(Page page) throws Exception;
}
