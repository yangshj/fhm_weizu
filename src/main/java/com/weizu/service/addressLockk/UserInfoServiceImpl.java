package com.weizu.service.addressLockk;

import java.util.List;

import com.weizu.pojo.addressBook.WeChatAPPBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.dao.addressBook.UserInfoDao;
import com.weizu.pojo.addressBook.UserInfoBean;

@Service("userInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public UserInfoBean findUserByOpenId(UserInfoBean bean) throws Exception {
		return userInfoDao.findUserByOpenId(bean);
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
	public List<UserInfoBean> getAllUserNoAuth(WeChatAPPBean bean) throws Exception {
		return userInfoDao.getAllUserNoAuth(bean);
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
