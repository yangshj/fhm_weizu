package com.weizu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weizu.dao.UserInfoDao;
import com.weizu.pojo.UserInfoBean;

@Service("userInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public UserInfoBean findUserByOpenId(String openId) throws Exception {
		return userInfoDao.findUserByOpenId(openId);
	}

	@Override
	public Integer inserWeiZuUser(UserInfoBean bean) throws Exception {
		return userInfoDao.inserWeiZuUser(bean);
	}

	

}
