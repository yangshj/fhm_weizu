package com.weizu.service;

import com.weizu.pojo.UserInfoBean;

public interface UserInfoService {
	
	/** 通过openId查找用户信息 */
	UserInfoBean findUserByOpenId(String openId)  throws Exception;
	
	/** 插入用户信息 */
	Integer inserWeiZuUser(UserInfoBean bean) throws Exception;
	
	
}
