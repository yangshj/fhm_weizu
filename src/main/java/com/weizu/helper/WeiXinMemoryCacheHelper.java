package com.weizu.helper;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.UuidUtil;
import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.ITimeOutMap.ITimeOutHandler;
import com.weizu.core.queue.TimeOutForCreateDateMap;
import com.weizu.util.StringUtil;
import com.weizu.util.UUIDUtil;
import com.weizu.util.WXAppletUserInfoUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 避免每次授权，存储10天的openId
 * @author songlm
 *
 */
//@Deprecated
public class WeiXinMemoryCacheHelper {

	// 微信OAuth认证的时候，服务器内存的方式缓存UserOpenId; key=session ，value=UserOpenId
	private static ITimeOutMap<String, UserOpenInfo> sessionOpenIdMap = new TimeOutForCreateDateMap<String, UserOpenInfo>();

	static {
		sessionOpenIdMap.setTimeOut(10 * 24 * 60 * 60 * 1000); // 10天
		sessionOpenIdMap.setCustomTimeOutHandler(new ITimeOutHandler<String, UserOpenInfo>() {
			@Override
			public void timeOut(String key, UserOpenInfo UserOpenId) {
				sessionOpenIdMap.remove(key);
			}
		});
	}

	// 处理openid缓存
	public synchronized static UserOpenInfo getOpenidByCode(String code) {
		if (StringUtil.isNotEmpty(code)) {
			JSONObject jsonObject = WXAppletUserInfoUtil.getSessionKeyOropenid(code);
			String openId = (String)jsonObject.get("openid");
			String sessionkey = (String)jsonObject.get("session_key");
			System.out.println("微信返回原始session:"+sessionkey);
			// 自定义session
			String customSession = UUIDUtil.getUUID();
			UserOpenInfo userOpenInfo = new UserOpenInfo();
			userOpenInfo.setOpenId(openId);
			userOpenInfo.setCreateTime(new Date());
			userOpenInfo.setSessionId(customSession);
			setOpenid(customSession, userOpenInfo);
			return userOpenInfo;
		}
		return null;
	}
	
	public static UserOpenInfo getOpenidBySessionId(String sessionId){
		UserOpenInfo userOpenInfo = sessionOpenIdMap.get(sessionId);
		if(userOpenInfo!=null){
			userOpenInfo.setUpdateTime(new Date());
		}
		return userOpenInfo;
	}

	public synchronized static void setOpenid(String session, UserOpenInfo userOpenInfo) {
		if (StringUtil.isNotEmpty(session) && userOpenInfo!=null && StringUtil.isNotEmpty(userOpenInfo.getOpenId())) {
			System.out.println("放进去……"+session);
			sessionOpenIdMap.put(session, userOpenInfo);
		}
	}

	/**
	 * 获取所有在线用户
	 * @return
	 */
	public static List<UserOpenInfo> getUserOpenInfo(){
		List<UserOpenInfo> valueList = new ArrayList<UserOpenInfo>(sessionOpenIdMap.values());
		return valueList;
	}
}
