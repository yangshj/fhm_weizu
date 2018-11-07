package com.weizu.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.util.RightsHelper;
import com.weizu.common.amap.GisInfo;
import com.weizu.helper.ResultHelper;
import com.weizu.pojo.*;
import com.weizu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeiXinMemoryCacheHelper;

@Controller
@RequestMapping(value="/weizu/weixin")
public class WeiXinController extends BaseController{


    @Autowired
    private SurNameService surNameService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserLocationService userLocation;
	@Autowired
	private AddressLookService addressLookService;
	@Autowired
	private AddressLookAuthService addressLookAuthService;
	
	@RequestMapping(value="/backLocation")
	@ResponseBody
	 public String backLocation(HttpServletRequest request, HttpServletResponse response){
		BackLocationRe re = new BackLocationRe();
		try {
			re.setResult(ResultHelper.FAIL);
			String sessionId = request.getParameter("sessionId");
			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				UserInfoBean exit = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
				if(exit!=null){
					WeiZuLocationBean bean = new WeiZuLocationBean();
					bean.setUserId(exit.getId());
					bean.setLatitude(Double.parseDouble(latitude));
					bean.setLongitude(Double.parseDouble(longitude));
					GisInfo gisInfo =  new GisInfo(bean.getLongitude(), bean.getLatitude());
					String locationInfo = gisInfo.getContent();
					bean.setLocationInfo(locationInfo);
					userLocation.insertLocation(bean);
					re.setResult(ResultHelper.SUCCESS);
				}
			} else {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
			System.out.println("成功……");
		} catch (Exception e) {
			re.setResult(ResultHelper.FAIL);
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
			UserInfoBean exit = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
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
				userInfoService.inserWeiZuUser(bean);
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
					    if(marker.getIconPath()==null) continue;
						int end = marker.getIconPath().lastIndexOf("/");
						String temp = marker.getIconPath().substring(0, end);
						System.out.println("temp: "+temp);
						temp += "/46";
						marker.setIconPath(temp);
					}
					re.setMarkers(list);
					re.setResult(ResultHelper.SUCCESS);
				} 
			} else {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
		}catch(Exception e){
		    e.printStackTrace();
			re.setResult(ResultHelper.FAIL);
		}
		return JSON.toJSONString(re);
	}
	
	
	@RequestMapping(value="/getAddressLooks")
	public void getAddressLooks(HttpServletRequest request, HttpServletResponse response){
		AddressLookRE re = new AddressLookRE();
		try{
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			re.setResult(ResultHelper.FAIL);
			String sessionId = request.getParameter("sessionId");
			String surname = request.getParameter("surname");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
			    SurNameBean surNameBean = null;
			    List<SurNameBean> surnameList = surNameService.getAllSurName();
                for(SurNameBean bean : surnameList){
                    if(bean.getSurname().equals(surname)){
                        surNameBean = bean;
                        break;
                    }
                }
                if(surNameBean!=null){
                    AddressLookBean param = new AddressLookBean();
                    param.setSurnameId(surNameBean.getId());
                    List<AddressLookBean> list = addressLookService.findAddressLookByCondition(param);
                    if(list!=null && list.size()>0){
                        re.setListData(list);
                        re.setResult(ResultHelper.SUCCESS);
                    }
                }
			} else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}catch(Exception e){
			re.setResult(ResultHelper.FAIL);
		}
	}

	// 获取姓氏目录
    @RequestMapping(value="/getSurNames")
	public void getSurNames(HttpServletRequest request, HttpServletResponse response){
        SurNameBeanRE re = new SurNameBeanRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                List<SurNameBean> list = surNameService.getAllSurName();
                re.setListData(list);
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }catch(Exception e){
            re.setResult(ResultHelper.FAIL);
        }
    }

	// 判断通讯录界面权限
    @RequestMapping(value="/judgeAddressLookAuthority")
	public void judgeAddressLookAuthority(HttpServletRequest request, HttpServletResponse response){
        AddressLookRE re = new AddressLookRE();
        try{
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String surname = request.getParameter("surname");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfo = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                if(userInfo!=null){
                    List<SurNameBean> list = surNameService.getAllSurName();
                    if(list!=null && list.size()>0){
                        for(SurNameBean surNameBean: list){
                            if(surNameBean.getSurname().equals(surname)){
                                String rights = userInfo.getRights();
                                if(rights!=null && rights!=""){
                                    Boolean hasRights = RightsHelper.testRights(rights, surNameBean.getId().intValue());
                                    if(hasRights){
                                        re.setResult(ResultHelper.SUCCESS);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }catch(Exception e){
            re.setResult(ResultHelper.FAIL);
        }
    }

	/**
	 *  上传请求权限信息
	 *  @return {sessionId:""};
	 */
	@RequestMapping(value="/uploadAuthRequest")
	@ResponseBody
	public String uploadAuthRequest(HttpServletRequest request, HttpServletResponse response){
        AddressLookRE re = new AddressLookRE();
		String sessionId = request.getParameter("sessionId");
		String requestInfo = request.getParameter("requestInfo");
		String surname = request.getParameter("surname");
		String nickName = request.getParameter("nickName");
		try {
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
                UserInfoBean userInfo = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                if(userInfo!=null){
                    AddressLookAuthRequestBean bean = new AddressLookAuthRequestBean();
                    bean.setUserId(userInfo.getId());
                    bean.setNickName(nickName);
                    bean.setSurname(surname);
                    bean.setRequestInfo(requestInfo);
                    re.setResult(ResultHelper.SUCCESS);
                    addressLookAuthService.insertAuthRequest(bean);
                }
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(re);
	}
}
