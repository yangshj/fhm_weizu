package com.weizu.service;

import java.util.List;

import com.weizu.pojo.UserLocationMarkerBean;
import com.weizu.pojo.WeiZuLocationBean;

public interface UserLocationService {
	
	/** 插入位置信息 */
	Integer insertLocation(WeiZuLocationBean bean) throws Exception;
	
	/** 获取所有用户最近的位置信息 */
	List<UserLocationMarkerBean> getAllUserLatelyLocaitons() throws Exception;
}
