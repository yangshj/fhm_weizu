package com.weizu.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.weizu.pojo.SurNameBean;
import com.weizu.pojo.UserInfoBean;
import com.weizu.service.AddressLookService;
import com.weizu.service.SurNameService;
import com.weizu.service.UserInfoService;
import com.weizu.util.StringUtil;

@Controller
@RequestMapping(value="/weizu/user")
public class UserInfoController extends BaseController{
	
	@Autowired
	private AddressLookService addressLookService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SurNameService surNameService;
	
	/**
	 * 显示用户列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView listUsers(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String USERNAME = pd.getString("USERNAME");
			if(null != USERNAME && !"".equals(USERNAME)){
				USERNAME = USERNAME.trim();
				pd.put("USERNAME", USERNAME);
			}
			page.setPd(pd);
			List<PageData>	userList = userInfoService.getAllUserInfoListPage(page);	//列出用户列表
			for(PageData pageData : userList){
				String url = (String) pageData.get("avatarUrl");
				if(StringUtil.isNotEmpty(url)){
					int end = url.lastIndexOf("/");
					String temp = url.substring(0, end);
					System.out.println("temp: "+temp);
					pageData.put("avatarUrl_46", temp+"/46");
					pageData.put("avatarUrl_0", temp+"/0");
				}
			}
			mv.setViewName("weizu/user/list");
			mv.addObject("userList", userList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 请求角色菜单授权页面
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String userId, Model model)throws Exception{
		try{
			List<SurNameBean> surList = surNameService.getAllSurName();
			List<Menu> menuList = new ArrayList<>();
			UserInfoBean userInfo = userInfoService.findUserById(userId);
				String roleRights = userInfo.getRights();
				for(SurNameBean surNameBean : surList){
					Menu menu = new Menu();
					menu.setMENU_ID(surNameBean.getId().toString());
					menu.setMENU_NAME(surNameBean.getSurname());
					if(roleRights!=null && roleRights!=null){
						menu.setHasMenu(RightsHelper.testRights(roleRights, surNameBean.getId().intValue()));
					}
					menuList.add(menu);
				}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("userId", userId);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "weizu/user/authorization";
	}
	
	/**
	 * 保存角色菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String userId, @RequestParam String menuIds, PrintWriter out)throws Exception{
		PageData pd = new PageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				UserInfoBean userInfo = userInfoService.findUserById(userId);
				userInfo.setRights(rights.toString());
				userInfo.setId(Long.parseLong(userId));
				userInfoService.updateUserById(userInfo);
				pd.put("rights",rights.toString());
			}else{
				UserInfoBean userInfo = new UserInfoBean();
				userInfo.setRights("");
				userInfo.setId(Long.parseLong(userId));
				userInfoService.updateUserById(userInfo);
				pd.put("rights","");
			}
			pd.put("userId", userId);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
}
