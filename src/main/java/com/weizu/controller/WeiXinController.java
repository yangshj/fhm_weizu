package com.weizu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.weizu.common.amap.GisInfo;
import com.weizu.helper.ResultHelper;
import com.weizu.pojo.*;
import com.weizu.service.*;
import com.weizu.util.StringUtil;
import com.weizu.util.StringUtils;
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
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
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
		String usrString = "code: "+code +" nickName: "+nickName+" avatarUrl: "+avatarUrl;
		System.out.println(usrString);
        Long start = System.currentTimeMillis();
		UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidByCode(code);
		Long end = System.currentTimeMillis();
        System.out.println("获取openId耗时: "+(end-start));
		try {
			System.out.println("成功……"+userOpenInfo + "usrString: "+usrString);
			UserInfoBean exit = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
			System.out.println("已经存在: " + "usrString: "+usrString + " " + JSON.toJSONString(exit));
			if(exit==null){
				UserInfoBean bean = new UserInfoBean();
				bean.setAvatarUrl(avatarUrl);
				bean.setCity(city);
				bean.setCountry(country);
				if(StringUtil.isNotEmpty(gender)){
					bean.setGender(Integer.parseInt(gender));
				}
				bean.setLanguage(language);
				bean.setNickName(nickName);
				bean.setOpenId(userOpenInfo.getOpenId());
				bean.setProvince(province);
				userInfoService.inserWeiZuUser(bean);
			} else {
			    // 更新数据库昵称和头像为空的数据
			    if(StringUtil.isEmpty(exit.getNickName()) && StringUtil.isNotEmpty(nickName)){
                    UserInfoBean bean = exit;
                    bean.setAvatarUrl(avatarUrl);
                    bean.setCity(city);
                    bean.setCountry(country);
                    if(StringUtil.isNotEmpty(gender)){
                        bean.setGender(Integer.parseInt(gender));
                    }
                    bean.setLanguage(language);
                    bean.setNickName(nickName);
                    bean.setProvince(province);
                    System.out.println("更新数据库昵称: "+nickName);
                    userInfoService.updateUserById(bean);
                } else {
					System.out.println("昵称不存在无法更新： "+usrString);
				}
                if(StringUtil.isNotEmpty(exit.getManagerRights()) && !exit.getManagerRights().equals("0")){
					userOpenInfo.setManager(true);
				}
				if(exit.getAdmin()!=null && exit.getAdmin().intValue()==1){
                    userOpenInfo.setAdmin(true);
                }
            }
			//return "{sessionId:"+userOpenInfo.getSessionId()+"}";
		} catch (Exception e) {
			System.out.println("获取用户openId异常: "+JSON.toJSONString(e));
			e.printStackTrace();
		}
		return JSON.toJSONString(userOpenInfo);
	}
	
	@RequestMapping(value="/getAllUserLocations")
	@ResponseBody
	public void getAllUserLocations(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserLocationRe re = new UserLocationRe();
		try{
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			re.setResult("fail");
			String sessionId = request.getParameter("sessionId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				List<UserLocationMarkerBean> list = userLocation.getAllUserLatelyLocaitons();
				if(list!=null && list.size()>0){
					for(UserLocationMarkerBean marker : list){
					    if(StringUtil.isEmpty(marker.getIconPath())) continue;
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
		} finally {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}
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
				UserInfoBean userInfoBean = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
			    SurNameBean surNameBean = null;
			    List<SurNameBean> surnameList = surNameService.getAllSurName();
                for(SurNameBean bean : surnameList){
                    if(bean.getSurname().equals(surname)){
                        surNameBean = bean;
                        break;
                    }
                }
                if(surNameBean!=null){
					if(userInfoBean!=null){
						Boolean hasRights = RightsHelper.testRights(userInfoBean.getManagerRights(), surNameBean.getId().intValue());
						re.setManagerRights(hasRights);
					}
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
		String modifyOrAdd  = request.getParameter("modifyOrAdd");
		String modifyId = request.getParameter("modifyId");
		String userName = request.getParameter("userName");
		String mobilePhone = request.getParameter("mobilePhone");
		String manager = request.getParameter("manager");
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
                UserInfoBean userInfo = userInfoService.findUserByOpenId(userOpenInfo.getOpenId());
                // 新增
                if(StringUtil.isEmpty(modifyOrAdd) || StringUtil.isEmpty(manager) || modifyOrAdd.equals("add")){
					if(userInfo!=null){
						AddressLookAuthRequestBean bean = new AddressLookAuthRequestBean();
						bean.setUserId(userInfo.getId());
						bean.setNickName(nickName);
						bean.setSurname(surname);
						bean.setRequestInfo(requestInfo);
						re.setResult(ResultHelper.SUCCESS);
						addressLookAuthService.insertAuthRequest(bean);
					}
					// 管理员
					if(StringUtil.isNotEmpty(manager) && manager.equals("true")){
						List<SurNameBean> surNameBeanList = surNameService.getAllSurName();
						SurNameBean surNameBean = null;
						for(SurNameBean temp : surNameBeanList){
							if(temp.getSurname().equals(surname)){
								surNameBean = temp;
								break;
							}
						}
						if(surNameBean!=null){
							AddressLookBean param = new AddressLookBean();
							param.setSurnameId(surNameBean.getId());
							param.setMobilePhone(mobilePhone);
							param.setUserName(userName);
							addressLookService.inserAddressLook(param);
						}
					}
				}
				// 修改模式
				else if(StringUtil.isNotEmpty(modifyOrAdd) && modifyOrAdd.equals("modify")){
					Long id = Long.parseLong(modifyId);
					AddressLookBean param = new AddressLookBean();
					param.setId(id);
					AddressLookBean bean = addressLookService.findAddressLookById(param);
					if(bean!=null){
						bean.setUserName(userName);
						bean.setMobilePhone(mobilePhone);
						addressLookService.updateAddressLook(bean);
					}
					re.setResult(ResultHelper.SUCCESS);
				}
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(re);
	}

	/**
	 *  获取所有没有访问权限的用户
	 */
	@RequestMapping(value="/getAllUserNoAuth")
	@ResponseBody
	public void getAllUserNoAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserInfoRE re = new UserInfoRE();
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			String sessionId = request.getParameter("sessionId");
			re.setResult(ResultHelper.FAIL);
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null) {
				List<UserInfoBean> list =  userInfoService.getAllUserNoAuth();
				re.setListData(list);
				re.setResult(ResultHelper.SUCCESS);
			} else {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
		} catch (Exception e) {
			re.setResult(ResultHelper.FAIL);
			e.printStackTrace();
		} finally {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}
	}

    /**
     *  获取所有用户
     */
    @RequestMapping(value="/getAllUser")
    @ResponseBody
    public void getAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserInfoRE re = new UserInfoRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String sessionId = request.getParameter("sessionId");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
                List<UserInfoBean> list =  userInfoService.getAllUserByCondition(null);
                re.setListData(list);
                re.setResult(ResultHelper.SUCCESS);
            } else {
                re.setResult(ResultHelper.SESSION_INVALID);
            }
        } catch (Exception e) {
            re.setResult(ResultHelper.FAIL);
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }

	/**
	 *  授权访问
	 */
	@RequestMapping(value="/authAccess")
	@ResponseBody
	public void authAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserInfoRE re = new UserInfoRE();
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			String sessionId = request.getParameter("sessionId");
			String userId = request.getParameter("userId");
			String authManager = request.getParameter("authManager");
			String openAuth = request.getParameter("openAuth");
			re.setResult(ResultHelper.FAIL);
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null) {
				List<SurNameBean> surNameBeanList = surNameService.getAllSurName();
				if(surNameBeanList!=null && surNameBeanList.size()>0){
					List<String> rights = new ArrayList<String>();
					for(SurNameBean bean : surNameBeanList){
						rights.add(bean.getId().toString());
					}
					BigInteger rightString = RightsHelper.sumRights(rights);
					UserInfoBean userInfoBean = new UserInfoBean();
					userInfoBean.setId(Long.parseLong(userId));
					if(authManager!=null && authManager.equals("true")){
					    if(openAuth!=null && openAuth.equals("true")){
					        re.setMsg("授权成功");
						    userInfoBean.setManagerRights(rightString.toString());
                        } else {
					        // 关闭权限
                            re.setMsg("解除授权成功");
                            userInfoBean.setManagerRights("0");
                        }
					} else {
						userInfoBean.setRights(rightString.toString());
					}
					userInfoService.updateUserById(userInfoBean);
				}
                re.setResult(ResultHelper.SUCCESS);
			} else {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
		} catch (Exception e) {
			re.setResult(ResultHelper.FAIL);
			re.setMsg("授权操作失败");
			e.printStackTrace();
		} finally {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}
	}

}
