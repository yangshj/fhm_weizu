package com.weizu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.entity.Page;
import com.fh.util.PageData;
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

	@Override
	public List<PageData> getAllUserInfoListPage(Page page) throws Exception {
		return userInfoDao.getAllUserInfoListPage(page);
	}

	@Override
	public List<UserInfoBean> getAllUserNoAuth() throws Exception {
		return userInfoDao.getAllUserNoAuth();
	}

	@Override
	public List<UserInfoBean> getAllUserByCondition(UserInfoBean bean) throws Exception {
		return userInfoDao.getAllUserByCondition(bean);
	}

	@Override
	public UserInfoBean findUserById(String userId) throws Exception {
		return userInfoDao.findUserById(userId);
	}

	@Override
	public Integer updateUserById(UserInfoBean bean) throws Exception {
		return userInfoDao.updateUserById(bean);
	}

	

}
