package com.weizu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.AddressLookBean;

@Repository("addressLookDaoImpl")
public class AddressLookDaoImpl  extends DaoSupport implements AddressLookDao{

	@Override
	public AddressLookBean findAddressLookById(AddressLookBean bean) throws Exception {
		return (AddressLookBean) this.findForObject("com.weizu.addressLook.findAddressLookById", bean);
	}

	@Override
	public Integer inserAddressLook(AddressLookBean bean) throws Exception {
		return (Integer) this.save("com.weizu.addressLook.inserAddressLook", bean);
	}

	@Override
	public Integer updateAddressLook(AddressLookBean bean) throws Exception {
		return (Integer) this.update("com.weizu.addressLook.updateAddressLook", bean);
	}

	@Override
	public void deleteAddressLook(AddressLookBean bean) throws Exception {
		this.delete("com.weizu.addressLook.deleteAddressLook", bean);
	}

	@Override
	public List<AddressLookBean> getAllAddressLook() throws Exception {
		return (List<AddressLookBean>) this.findForList("com.weizu.addressLook.getAllAddressLook", null);
	}

	@Override
	public List<PageData> getAllAddressLookListPage(Page page) throws Exception {
		return (List<PageData>) this.findForList("com.weizu.addressLook.getAllAddressLooklistPage", page);
	}

	@Override
	public AddressLookBean findAddressLookByCondition(AddressLookBean bean)
			throws Exception {
		return (AddressLookBean) this.findForObject("com.weizu.addressLook.findAddressLookByCondition", bean);
	}

}
