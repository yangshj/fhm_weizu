package com.weizu.service.addressLockk;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;

public interface UserInfoService {
	
	/** 通过openId查找用户信息 */
	UserInfoBean findUserByOpenId(UserInfoBean bean)  throws Exception;
	
	/** 通过Id查找用户信息 */
	UserInfoBean findUserById(String userId)  throws Exception;
	
	/** 通过Id更新用户信息 */
	Integer updateUserById(UserInfoBean bean) throws Exception;
	
	/** 插入用户信息 */
	Integer inserWeiZuUser(UserInfoBean bean) throws Exception;
	
	/** 获取用户信息信息 */
	List<PageData> getAllUserInfoListPage(Page page) throws Exception;

	/** 获取所有没有访问权限的用户 */
	List<UserInfoBean> getAllUserNoAuth(WeChatAPPBean bean) throws Exception;

	/** 获取所有用户 */
	List<UserInfoBean> getAllUserByCondition(UserInfoBean bean) throws Exception;
}
