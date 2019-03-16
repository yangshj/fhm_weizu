package com.weizu.controller;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.util.Const;
import com.weizu.common.amap.GisInfo;
import com.weizu.dao.addressBook.AddressLookDao;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.RightsHelper;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.pojo.addressBook.*;
import com.weizu.pojo.oa.BaseRE;
import com.weizu.service.addressLockk.*;
import com.weizu.util.FileUtil;
import com.weizu.util.StringUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
	private AddressLookDao addressLookDao;
	
	@RequestMapping(value="/backLocation")
	@ResponseBody
	 public void backLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BaseRE re = new BaseRE();
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			re.setResult(ResultHelper.FAIL);
			String sessionId = request.getParameter("sessionId");
			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			String appId = request.getParameter("appId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean query = new UserInfoBean();
				query.setAppId(weChatAPPBean.getId());
				query.setOpenId(userOpenInfo.getOpenId());
				UserInfoBean exit = userInfoService.findUserByOpenId(query);
				if(exit!=null) {
					WeiZuLocationBean bean = new WeiZuLocationBean();
					bean.setUserId(exit.getId());
					bean.setAppId(weChatAPPBean.getId());
					bean.setLatitude(Double.parseDouble(latitude));
					bean.setLongitude(Double.parseDouble(longitude));
					// 超过中国经纬度范围（经度范围：73°33′E至135°05′E, 纬度范围：3°51′N至53°33′N
					if (bean.getLongitude() > 73.55 && bean.getLongitude() < 135.084 && bean.getLatitude() > 3.85 && bean.getLatitude() < 53.55){
						GisInfo gisInfo =  new GisInfo(bean.getLongitude(), bean.getLatitude());
						String locationInfo = gisInfo.getContent();
						bean.setLocationInfo(locationInfo);
						userLocation.insertLocation(bean);
						if(exit.getRights().equals("0")){
							testRigthsByLocation(sessionId, latitude, longitude, exit, weChatAPPBean);
						}
					} else {
						System.out.println("经纬度超出范围: "+JSON.toJSONString(bean));

					}
					re.setResult(ResultHelper.SUCCESS);
				}
			} else {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
			System.out.println("成功……");
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
	 *  根据code,获取用户openId。
	 *  如果用户表已经存在次openId，直接返回sessionId。
	 *  如果用户表不存在次openId，先保存用户再返回sessionId。
	 *  @return {sessionId:""};
	 */
	@RequestMapping(value="/getSessionIdAndSaveUserInfo", method = {RequestMethod.POST})
	@ResponseBody
	public String getOpenIdAndSaveUserInfo(HttpServletRequest request, HttpServletResponse response){
		UserOpenInfo userOpenInfo = new UserOpenInfo();
		String code = request.getParameter("code");
		String avatarUrl = request.getParameter("avatarUrl");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String gender = request.getParameter("gender");
		String language = request.getParameter("language");
		String nickName = request.getParameter("nickName");
		String province = request.getParameter("province");
		// 特殊符号处理
		nickName = StringUtil.handleSpecial(nickName);
		country =  StringUtil.handleSpecial(country);
		province =  StringUtil.handleSpecial(province);
		city =  StringUtil.handleSpecial(city);
		String usrString = "code: "+code +" nickName: "+nickName+" avatarUrl: "+avatarUrl;
		System.out.println(usrString);
        Long start = System.currentTimeMillis();
		Long end = System.currentTimeMillis();
        System.out.println("获取openId耗时: "+(end-start));
		try {
			String appId = request.getParameter("appId");
			WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
			if(weChatAPPBean==null){
				userOpenInfo.setResult(ResultHelper.FAIL);
				return JSON.toJSONString(userOpenInfo);
			}
			userOpenInfo = WeiXinMemoryCacheHelper.getOpenidByCode(weChatAPPBean,code);
			System.out.println("成功……"+userOpenInfo + "usrString: "+usrString);
			UserInfoBean query = new UserInfoBean();
			query.setAppId(weChatAPPBean.getId());
			query.setOpenId(userOpenInfo.getOpenId());
			UserInfoBean exit = userInfoService.findUserByOpenId(query);
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
				bean.setAppId(weChatAPPBean.getId());
				userInfoService.inserWeiZuUser(bean);
			} else {
			    // 更新数据库昵称和头像为空的数据
			    if(StringUtil.isNotEmpty(avatarUrl) && !avatarUrl.equals(exit.getAvatarUrl())){
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
					bean.setAppId(weChatAPPBean.getId());
                    System.out.println("更新数据库昵称: "+nickName);
                    userInfoService.updateUserById(bean);
                } else {
					System.out.println("头像没发生变化不更新： "+usrString);
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
			String appId = request.getParameter("appId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				List<UserLocationMarkerBean> list = userLocation.getAllUserLatelyLocaitons(weChatAPPBean);
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
			String appId = request.getParameter("appId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null){
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean query =  new UserInfoBean();
				query.setAppId(weChatAPPBean.getId());
				query.setOpenId(userOpenInfo.getOpenId());
				UserInfoBean userInfoBean = userInfoService.findUserByOpenId(query);
			    SurNameBean surNameBean = null;
			    List<SurNameBean> surnameList = surNameService.getAllSurName(weChatAPPBean);
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
                    param.setAppId(weChatAPPBean.getId());
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
			String appId = request.getParameter("appId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
                List<SurNameBean> list = surNameService.getAllSurName(weChatAPPBean);
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
			String latitude = request.getParameter("latitude");
			String longitude = request.getParameter("longitude");
			String appId = request.getParameter("appId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			UserInfoBean userInfo = null;
			if(userOpenInfo!=null){
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean query = new UserInfoBean();
				query.setOpenId(userOpenInfo.getOpenId());
				query.setAppId(weChatAPPBean.getId());
                userInfo = userInfoService.findUserByOpenId(query);
                if(userInfo!=null){
                    List<SurNameBean> list = surNameService.getAllSurName(weChatAPPBean);
                    if(list!=null && list.size()>0){
                        for(SurNameBean surNameBean: list){
                            if(surNameBean.getSurname().equals(surname)){
                                String rights = userInfo.getRights();
                                if(rights!=null && rights!=""){
                                    Boolean hasRights = RightsHelper.testRights(rights, surNameBean.getId().intValue());
                                    if(hasRights){
                                        re.setResult(ResultHelper.SUCCESS);
										// session 置空，重新登录
										if(userOpenInfo!=null && !userOpenInfo.getManager() && StringUtil.isNotEmpty(userInfo.getManagerRights())){
											Boolean hasManagerRights = RightsHelper.testRights(userInfo.getManagerRights(), surNameBean.getId().intValue());
											if(hasManagerRights){
												WeiXinMemoryCacheHelper.clearSession(sessionId);
											}
										}
                                    }
                                }
                            }
                        }
                    }
                    // 根据位置来判断是否有权限
					if(re.getResult().equals(ResultHelper.FAIL) && StringUtil.isNotEmpty(latitude) && StringUtil.isNotEmpty(longitude)){
						boolean rights = testRigthsByLocation(sessionId, latitude, longitude, userInfo, weChatAPPBean);
						if(rights){
							re.setResult(ResultHelper.SUCCESS);
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
	 * 通过位置判断是否有权限，在西玉曹的自动获取权限
	 * @param latitude	维度
	 * @param longitude	精度
	 * @param userInfo	用户信息
	 */
    public boolean testRigthsByLocation(String sessionId, String latitude, String longitude, UserInfoBean userInfo, WeChatAPPBean weChatAPPBean) {
    	try{
			if(StringUtil.isNotEmpty(latitude) && StringUtil.isNotEmpty(longitude) && StringUtil.isNotEmpty(sessionId)){
				boolean rights = RightsHelper.hasRights(weChatAPPBean, Double.parseDouble(latitude), Double.parseDouble(longitude));
				if(rights){
					if(userInfo!=null){
						List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
						if(surNameBeanList!=null && surNameBeanList.size()>0){
							List<String> rightList = new ArrayList<String>();
							for(SurNameBean bean : surNameBeanList){
								rightList.add(bean.getId().toString());
							}
							BigInteger rightString = RightsHelper.sumRights(rightList);
							UserInfoBean userInfoBean = new UserInfoBean();
							userInfoBean.setId(userInfo.getId());
							userInfoBean.setRights(rightString.toString());
							userInfoBean.setManagerRights(rightString.toString());
							userInfoService.updateUserById(userInfoBean);
							UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
							// session 置空，重新登录
							if(userOpenInfo!=null && !userOpenInfo.getManager() && rightString.compareTo(new BigInteger("0"))>0){
								WeiXinMemoryCacheHelper.clearSession(sessionId);
							}
						}
					}
					return true;
				}
			}
    	}catch (Exception e){
    		e.printStackTrace();
    		logger.error(e);
		}
		return false;
	}

	/**
	 *  上传请求权限信息
	 *  @return {sessionId:""};
	 */
	@RequestMapping(value="/uploadAuthRequest")
	@ResponseBody
	public void uploadAuthRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		String appId = request.getParameter("appId");
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean params = new UserInfoBean();
				params.setOpenId(userOpenInfo.getOpenId());
				params.setAppId(weChatAPPBean.getId());
                UserInfoBean userInfoBean = userInfoService.findUserByOpenId(params);
                // 新增
                if(StringUtil.isEmpty(modifyOrAdd) || StringUtil.isEmpty(manager) || modifyOrAdd.equals("add")){
					if(userInfoBean!=null){
						AddressLookAuthRequestBean bean = new AddressLookAuthRequestBean();
						bean.setUserId(userInfoBean.getId());
						bean.setNickName(nickName);
						bean.setSurname(surname);
						bean.setRequestInfo(requestInfo);
						bean.setAppId(weChatAPPBean.getId());
						re.setResult(ResultHelper.SUCCESS);
						addressLookAuthService.insertAuthRequest(bean);
					}
					// 管理员
					if(StringUtil.isNotEmpty(manager) && manager.equals("true")){
						List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
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
							param.setAppId(weChatAPPBean.getId());
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
					if(bean!=null && StringUtil.isNotEmpty(userName) && StringUtil.isNotEmpty(mobilePhone)){
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
		} finally {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}
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
			String appId = request.getParameter("appId");
			re.setResult(ResultHelper.FAIL);
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null) {
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				List<UserInfoBean> list =  userInfoService.getAllUserNoAuth(weChatAPPBean);
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
			String appId = request.getParameter("appId");
            re.setResult(ResultHelper.FAIL);
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null) {
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean bean = new UserInfoBean();
				bean.setAppId(weChatAPPBean.getId());
                List<UserInfoBean> list =  userInfoService.getAllUserByCondition(bean);
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
			String appId = request.getParameter("appId");
			re.setResult(ResultHelper.FAIL);
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null) {
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
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

	@RequestMapping("uploadImage")
	@ResponseBody
	public void uploadHeadImage(HttpServletRequest request, HttpServletResponse response,
								@RequestParam(value = "imgFile" , required=false) MultipartFile imageFile) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
        SaveUserInfoRE re = new SaveUserInfoRE();
		try {
			re.setResult(ResultHelper.FAIL);
			String sessionId = request.getParameter("sessionId");
			String surname =  request.getParameter("surname");
			String userName =  request.getParameter("userName");
			String userId =  request.getParameter("userId");
			String upAvatar =  request.getParameter("upAvatar");
			String mobilePhone =  request.getParameter("mobilePhone");
			String officePhone =  request.getParameter("officePhone");
			String sex =  request.getParameter("sex");
			String remark =  request.getParameter("remark");
			String appId = request.getParameter("appId");
			UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
			if(userOpenInfo!=null) {
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					re.setResult(ResultHelper.FAIL);
					re.setMsg("无效的appId");
					return;
				}
				UserInfoBean query1 = new UserInfoBean();
				query1.setAppId(weChatAPPBean.getId());
				query1.setOpenId(userOpenInfo.getOpenId());
				UserInfoBean userInfoBean = userInfoService.findUserByOpenId(query1);
				// 修改
				if(StringUtil.isNotEmpty(userId)){
					AddressLookBean param = new AddressLookBean();
					param.setId(Long.parseLong(userId));
					AddressLookBean bean = addressLookService.findAddressLookById(param);
					if(bean!=null){
						if(StringUtil.isNotEmpty(upAvatar) && upAvatar.equals("true")){
							String headImage = saveImage(request,userId,imageFile);
							bean.setHeadImage(headImage);
						}
						if(StringUtil.isNotEmpty(userName)) bean.setUserName(userName);
						if(StringUtil.isNotEmpty(mobilePhone)) bean.setMobilePhone(mobilePhone);
						if(StringUtil.isNotEmpty(officePhone)) bean.setOfficePhone(officePhone);
						if(StringUtil.isNotEmpty(sex)) bean.setSex(Integer.parseInt(sex));
						if(StringUtil.isNotEmpty(remark)) bean.setRemark(remark);
						addressLookService.updateAddressLook(bean);
                        // 历史记录
                        AddressLookAuthRequestBean requestBean = new AddressLookAuthRequestBean();
                        requestBean.setUserId(userInfoBean.getId());
                        requestBean.setNickName(userInfoBean.getNickName());
                        requestBean.setSurname(surname);
                        requestBean.setRequestInfo("修改: "+JSON.toJSONString(bean));
                        requestBean.setAppId(weChatAPPBean.getId());
                        addressLookAuthService.insertAuthRequest(requestBean);
                        re.setResult(ResultHelper.SUCCESS);
					} else {
						re.setMsg("保存失败userId不存在"+userId);
						re.setResult(ResultHelper.FAIL);
						return;
					}
				}
				// 新增
				else {
					List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
					SurNameBean surNameBean = null;
					for(SurNameBean temp : surNameBeanList){
						if(temp.getSurname().equals(surname)){
							surNameBean = temp;
							break;
						}
					}
					if(surNameBean!=null){
						AddressLookBean query = new AddressLookBean();
						query.setUserName(userName);
						query.setMobilePhone(mobilePhone);
						query.setAppId(weChatAPPBean.getId());
						List<AddressLookBean> exist = addressLookDao.findAddressLookByCondition(query);
						if(exist!=null && exist.size()>0){
							re.setResult(ResultHelper.FAIL);
							re.setMsg("用户: "+userName +"已经存在");
							return;
						}
						AddressLookBean param = new AddressLookBean();
						param.setSurnameId(surNameBean.getId());
						param.setUserName(userName);
						param.setOfficePhone(officePhone);
						param.setMobilePhone(mobilePhone);
						param.setAppId(weChatAPPBean.getId());
						if(StringUtil.isNotEmpty(sex)) param.setSex(Integer.parseInt(sex));
						param.setRemark(remark);
						Integer id = addressLookService.inserAddressLook(param);
						if(StringUtil.isNotEmpty(upAvatar) && upAvatar.equals("true")){
							String headImage = saveImage(request,id.toString(),imageFile);
                            param.setHeadImage(headImage);
                            param.setId(Long.valueOf(id.toString()));
							addressLookService.updateAddressLook(param);
						}
						// 历史记录
                        AddressLookAuthRequestBean bean = new AddressLookAuthRequestBean();
                        bean.setUserId(userInfoBean.getId());
                        bean.setNickName(userInfoBean.getNickName());
                        bean.setSurname(surname);
                        bean.setRequestInfo("新增: "+JSON.toJSONString(param));
                        bean.setAppId(weChatAPPBean.getId());
                        addressLookAuthService.insertAuthRequest(bean);
                        re.setAddressLookId(param.getId());
                        re.setResult(ResultHelper.SUCCESS);
					} else {
                        re.setResult(ResultHelper.FAIL);
                        re.setMsg("保存失败"+surname+"不存在");
                        return;
                    }
				}
				re.setResult(ResultHelper.SUCCESS);
				re.setMsg("保存成功");
			} else  {
				re.setResult(ResultHelper.SESSION_INVALID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(re));
			writer.flush();
		}
	}

    /**
     * 存储图片，返回外界能访问的URL路径。而非磁盘路径
	 * 1、微信头像有0、46、64、96、132数值可选，0代表132*132正方形头像）
	 * 2、存储原图，并压缩一份132的图像
	 * 3、存储路径如：
	 * http://localhost:8081/FHM/uploadFiles/uploadHeadImage/3/1542459722654/0.png
	 * http://localhost:8081/FHM/uploadFiles/uploadHeadImage/3/1542459722654/132.png
     * @return 压缩的132的图像的路径
     * @throws Exception
     */
	public String saveImage(HttpServletRequest request,String userId, MultipartFile imageFile) throws Exception {
		String realPath = request.getSession().getServletContext().getRealPath("/")+ Const.uploadHeadImage;
		// 根据配置文件获取服务器图片存放路径
		String newFileName = "0";
		String newFileName_132 =  "132";
		String saveFilePath = userId+"/"+String.valueOf( System.currentTimeMillis())+"/";
		/* 构建文件目录 */
		File fileDir = new File(realPath+saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		//上传的文件名
		String uploadFileName = imageFile.getOriginalFilename();
		//文件的扩张名
		String extensionName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		//String imgPath = saveFilePath + newFileName + "." +extensionName;
		//原始图像 = 保存路径 + 文件名 + 扩展名
		String imgPath = saveFilePath + newFileName  + "." +extensionName;
		//132图像 = 保存路径 + 文件名_132 + 扩展名
		String imgPath_132 = saveFilePath + newFileName_132+ "." +extensionName;
		String fileName = realPath + imgPath;
		FileOutputStream out = new FileOutputStream(fileName);
		// 写入文件
		out.write(imageFile.getBytes());
		out.flush();
		out.close();
		// 压缩一份图片
		Thumbnails.of(fileName).scale(0.2f).outputQuality(0.2f).toFile(realPath + imgPath_132);
		// 服务器备份开始
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		System.out.println("os: "+os);
		// 备份存储
		if(os.contains("Linux")){
			String source = fileName;
			String target = "/usr/local/tomcat/images/"+imgPath;
			String target_132 = "/usr/local/tomcat/images/"+imgPath_132;
            System.out.println("图片备份至:"+ target);
			FileUtil.fileCopy(source, target);
			// 其中的scale是可以指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
			// 而outputQuality是图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
			//Thumbnails.of(source).scale(1f).outputQuality(1f).toFile(target);
			Thumbnails.of(source).scale(0.2f).outputQuality(0.5f).toFile(target_132);
		}
		System.out.println("图片上传至:"+ fileName);
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String result = basePath +  Const.uploadHeadImage + imgPath_132;
        System.out.println("图片地址转换为外界访问的URL: "+result);
		return result;
	}




}
