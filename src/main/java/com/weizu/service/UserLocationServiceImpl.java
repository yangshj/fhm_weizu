package com.weizu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.weizu.dao.UserLocationDao;
import com.weizu.pojo.UserLocationMarkerBean;
import com.weizu.pojo.WeiZuLocationBean;

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
}
