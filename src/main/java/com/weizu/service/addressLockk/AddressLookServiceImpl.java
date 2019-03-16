package com.weizu.service.addressLockk;

import java.util.List;

import com.weizu.pojo.addressBook.WeChatAPPBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.dao.addressBook.AddressLookDao;
import com.weizu.pojo.addressBook.AddressLookBean;

@Service("addressLookServiceImpl")
public class AddressLookServiceImpl implements AddressLookService{
	
	@Autowired
	private AddressLookDao addressLookDao;
	
	@Override
	public AddressLookBean findAddressLookById(AddressLookBean bean) throws Exception {
		return addressLookDao.findAddressLookById(bean);
	}

	@Override
	public Integer inserAddressLook(AddressLookBean bean) throws Exception {
		return addressLookDao.inserAddressLook(bean);
	}

	@Override
	public Integer updateAddressLook(AddressLookBean bean) throws Exception {
		return addressLookDao.updateAddressLook(bean);
	}

	@Override
	public void deleteAddressLook(AddressLookBean bean) throws Exception {
		addressLookDao.deleteAddressLook(bean);		
	}

	@Override
	public List<AddressLookBean> getAllAddressLook(WeChatAPPBean bean) throws Exception {
		return addressLookDao.getAllAddressLook(bean);
	}

	@Override
	public List<PageData> getAllAddressLookListPage(Page page) throws Exception {
		return addressLookDao.getAllAddressLookListPage(page);
	}

	@Override
	public List<AddressLookBean> findAddressLookByCondition(AddressLookBean bean) throws Exception {
		return addressLookDao.findAddressLookByCondition(bean);
	}

}
