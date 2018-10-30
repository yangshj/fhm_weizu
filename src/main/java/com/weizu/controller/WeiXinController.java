package com.weizu.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.pojo.AddressLookBean;
import com.weizu.pojo.AddressLookRE;
import com.weizu.pojo.BackLocationRe;
import com.weizu.pojo.UserInfoBean;
import com.weizu.pojo.UserLocationMarkerBean;
import com.weizu.pojo.UserLocationRe;
import com.weizu.pojo.WeiZuLocationBean;
import com.weizu.service.AddressLookService;
import com.weizu.service.UserLocationService;
import com.weizu.service.UserInfoService;

@Controller
@RequestMapping(value="/weizu/weixin")
public class WeiXinController extends BaseController{
	
//	验证域名：weizu.site
//
//	TXT 记录： _dnsauth
//
//	记录值：201810131620062ifazk375vz8ilkx150trgybpa0f9b0ujw02spxm507tmr9wjd

// 又拍云  yangshj  Yang18910970262
// freessl 754179755@qq.com Yang18910970262
	
	@Autowired
	private UserInfoService weiZuService;
	@Autowired
	private UserLocationService userLocation;
	@Autowired
	private AddressLookService addressLookService;
	
	
	@RequestMapping(value="/backLocation")
	@ResponseBody
	 public String backLocation(HttpServletRequest request, HttpServletResponse response){
		BackLocationRe re = new BackLocationRe();
		try {
			String sessionId = request.getParameter("sessionId");
			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				UserInfoBean exit = weiZuService.findUserByOpenId(userOpenInfo.getOpenId());
				if(exit!=null){
					WeiZuLocationBean bean = new WeiZuLocationBean();
					bean.setUserId(exit.getId());
					bean.setLatitude(Double.parseDouble(latitude));
					bean.setLongitude(Double.parseDouble(longitude));
					userLocation.insertLocation(bean);
					re.setResult("success");
				}
			} else {
				re.setResult("fail");
			}
			System.out.println("成功……");
		} catch (Exception e) {
			re.setResult("fail");
			e.printStackTrace();
		}
		return JSON.toJSONString(re);
	}
	
	/**
	 *  根据code,获取用户openId。
	 *  如果用户表已经存在次openId，直接返回sessionId。
	 *  如果用户表不存在次openId，先保存用户再返回sessionId。
	 *  @return {sessionId:""};
	 */
	@RequestMapping(value="/getSessionIdAndSaveUserInfo", method = {RequestMethod.POST})
	@ResponseBody
	public String getOpenIdAndSaveUserInfo(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("code");
		String avatarUrl = request.getParameter("avatarUrl");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String gender = request.getParameter("gender");
		String language = request.getParameter("language");
		String nickName = request.getParameter("nickName");
		String province = request.getParameter("province");
		System.out.println("code: "+code);
		// 测试
//		String customSession = UUIDUtil.getUUID();
//		if(customSession!=null){
//			return "{\"sessionId\":\""+customSession+"\"}";
//		}
		UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidByCode(code);
		try {
			System.out.println("成功……"+userOpenInfo);
			UserInfoBean exit = weiZuService.findUserByOpenId(userOpenInfo.getOpenId());
			if(exit==null){
				UserInfoBean bean = new UserInfoBean();
				bean.setAvatarUrl(avatarUrl);
				bean.setCity(city);
				bean.setCountry(country);
				bean.setGender(Integer.parseInt(gender));
				bean.setLanguage(language);
				bean.setNickName(nickName);
				bean.setOpenId(userOpenInfo.getOpenId());
				bean.setProvince(province);
				weiZuService.inserWeiZuUser(bean);
			}
			//return "{sessionId:"+userOpenInfo.getSessionId()+"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(userOpenInfo);
	}
	
	@RequestMapping(value="/getAllUserLocations")
	@ResponseBody
	public String getAllUserLocations(HttpServletRequest request, HttpServletResponse response){
		UserLocationRe re = new UserLocationRe();
		try{
			re.setResult("fail");
			String sessionId = request.getParameter("sessionId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				List<UserLocationMarkerBean> list = userLocation.getAllUserLatelyLocaitons();
				if(list!=null && list.size()>0){
					for(UserLocationMarkerBean marker : list){
						int end = marker.getIconPath().lastIndexOf("/");
						String temp = marker.getIconPath().substring(0, end);
						System.out.println("temp: "+temp);
						temp += "/46";
						marker.setIconPath(temp);
					}
					re.setMarkers(list);
					re.setResult("success");
				} 
			} 
		}catch(Exception e){
			re.setResult("fail");
		}
		return JSON.toJSONString(re);
	}
	
	
	@RequestMapping(value="/getAddressLooks")
	public void getAddressLooks(HttpServletRequest request, HttpServletResponse response){
		AddressLookRE re = new AddressLookRE();
		try{
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			re.setResult("fail");
			String sessionId = request.getParameter("sessionId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			//if(userOpenInfo!=null){
				List<AddressLookBean> list = addressLookService.getAllAddressLook();
				if(list!=null && list.size()>0){
					re.setListData(list);
					re.setResult("success");
				} 
			//} 
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}catch(Exception e){
			re.setResult("fail");
		}
	}
}
