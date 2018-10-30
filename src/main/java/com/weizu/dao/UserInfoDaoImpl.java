package com.weizu.dao;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.UserInfoBean;

@Repository("userInfoDaoImpl")
public class UserInfoDaoImpl extends DaoSupport implements UserInfoDao{

	@Override
	public UserInfoBean findUserByOpenId(String openId) throws Exception {
		return (UserInfoBean) this.findForObject("com.weizu.user.findUserByOpenId", openId);
	}

	@Override
	public Integer inserWeiZuUser(UserInfoBean bean) throws Exception {
		return (Integer) this.save("com.weizu.user.inserWeiZuUser", bean);
	}

}
