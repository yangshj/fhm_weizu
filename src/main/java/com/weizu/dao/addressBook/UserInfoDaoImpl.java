package com.weizu.dao.addressBook;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.UserInfoBean;

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

	@Override
	public List<PageData> getAllUserInfoListPage(Page page) throws Exception {
		return (List<PageData>) this.findForList("com.weizu.user.getAllUserInfolistPage", page);
	}

	@Override
	public List<UserInfoBean> getAllUserNoAuth() throws Exception {
		return (List<UserInfoBean>) this.findForList("com.weizu.user.getAllUserNoAuth", null);
	}

	@Override
	public List<UserInfoBean> getAllUserByCondition(UserInfoBean bean) throws Exception {
		return (List<UserInfoBean>) this.findForList("com.weizu.user.getAllUserByCondition", bean);
	}

	@Override
	public UserInfoBean findUserById(String userId) throws Exception {
		return (UserInfoBean) this.findForObject("com.weizu.user.findUserById", userId);
	}

	@Override
	public Integer updateUserById(UserInfoBean bean) throws Exception {
		return (Integer) this.update("com.weizu.user.updateUserById", bean);
	}

}
