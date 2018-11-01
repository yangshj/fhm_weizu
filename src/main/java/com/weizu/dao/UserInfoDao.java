package com.weizu.dao;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.UserInfoBean;

public interface UserInfoDao {
	
	/** 通过openId查找用户信息 */
	UserInfoBean findUserByOpenId(String openId)  throws Exception;
	
	/** 通过Id查找用户信息 */
	UserInfoBean findUserById(String userId)  throws Exception;
	
	/** 通过Id更新用户信息 */
	Integer updateUserById(UserInfoBean bean) throws Exception;
	
	/** 插入用户信息 */
	Integer inserWeiZuUser(UserInfoBean bean) throws Exception;
	
	/** 获取用户信息列表 */
	List<PageData> getAllUserInfoListPage(Page page) throws Exception;
}
