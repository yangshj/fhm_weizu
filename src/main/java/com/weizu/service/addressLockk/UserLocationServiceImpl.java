package com.weizu.service.addressLockk;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weizu.dao.addressBook.UserLocationDao;
import com.weizu.pojo.addressBook.UserLocationMarkerBean;
import com.weizu.pojo.addressBook.WeiZuLocationBean;

@Service("userLocationServiceImpl")
public class UserLocationServiceImpl implements UserLocationService{
	
	@Autowired
	private UserLocationDao userLocationDao;
	
	@Override
	public Integer insertLocation(WeiZuLocationBean bean) throws Exception {
		return userLocationDao.insertLocation(bean);
	}

	@Override
	public List<UserLocationMarkerBean> getAllUserLatelyLocaitons() throws Exception {
		return userLocationDao.getAllUserLatelyLocaitons();
	}

	@Override
	public List<PageData> getLocationListPage(Page page) throws Exception {
		return userLocationDao.getLocationListPage(page);
	}
}
