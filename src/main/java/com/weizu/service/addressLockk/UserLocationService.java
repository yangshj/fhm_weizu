package com.weizu.service.addressLockk;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.UserLocationMarkerBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.addressBook.WeiZuLocationBean;

public interface UserLocationService {
	
	/** 插入位置信息 */
	Integer insertLocation(WeiZuLocationBean bean) throws Exception;
	
	/** 获取所有用户最近的位置信息 */
	List<UserLocationMarkerBean> getAllUserLatelyLocaitons(WeChatAPPBean bean) throws Exception;

	/** 获取用户位置--页面列表 */
	List<PageData> getLocationListPage(Page page) throws Exception;
}
