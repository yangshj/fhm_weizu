package com.weizu.service;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.UserLocationMarkerBean;
import com.weizu.pojo.WeiZuLocationBean;

public interface UserLocationService {
	
	/** 插入位置信息 */
	Integer insertLocation(WeiZuLocationBean bean) throws Exception;
	
	/** 获取所有用户最近的位置信息 */
	List<UserLocationMarkerBean> getAllUserLatelyLocaitons() throws Exception;

	/** 获取用户位置--页面列表 */
	List<PageData> getLocationListPage(Page page) throws Exception;
}
