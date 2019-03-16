
package com.weizu.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weizu.helper.ResultHelper;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.pojo.addressBook.SurNameBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.service.addressLockk.SurNameService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.AddressLookBean;
import com.weizu.service.addressLockk.AddressLookService;

@Controller
@RequestMapping(value="/weizu/addressLook")
public class AddressLookController extends BaseController{
	
	String menuUrl = "weizu/addressLook/list.do"; //菜单地址(权限用)
	@Autowired
	private AddressLookService addressLookService;
	@Autowired
	private SurNameService surNameService;
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goUpdate")
	public ModelAndView goEditU(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if(pd.get("id")!=null){
				Long id = Long.parseLong((String) pd.get("id"));
				String appId = (String) pd.get("appId");
				WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
				if(weChatAPPBean==null){
					weChatAPPBean = WeChatAppHelper.getFirst();
					logger.info("默认");
				}
				AddressLookBean param = new AddressLookBean();
				param.setId(id);
				AddressLookBean bean = addressLookService.findAddressLookById(param);
				List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
				mv.addObject("surNameBeanList",surNameBeanList);
				mv.addObject("bean", bean);
			}
			mv.setViewName("weizu/addressLook/edit");
			mv.addObject("path", "update");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAddU(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String appId = (String) pd.get("appId");
			WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
			if(weChatAPPBean==null){
				weChatAPPBean = WeChatAppHelper.getFirst();
				logger.info("默认");
			}
			List<SurNameBean> surNameBeanList = surNameService.getAllSurName(weChatAPPBean);
			mv.addObject("surNameBeanList",surNameBeanList);
			mv.setViewName("weizu/addressLook/edit");
			mv.addObject("path", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 新增通讯录
	 */
	@RequestMapping(value="/save")
	public ModelAndView saveU(PrintWriter out) throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = new PageData();
			pd = this.getPageData();
			AddressLookBean param = new AddressLookBean();
			param.setUserName((String)pd.get("userName"));
			List<AddressLookBean> exits = addressLookService.findAddressLookByCondition(param);
			if(exits==null || exits.size()==0){
				AddressLookBean bean = new AddressLookBean();
				bean.setUserName((String)pd.get("userName"));
				bean.setMobilePhone((String)pd.get("mobilePhone"));
				bean.setSex(Integer.parseInt((String)pd.get("sex")));
				bean.setSurnameId(Long.parseLong((String) pd.get("surnameId")));
				addressLookService.inserAddressLook(bean);
				mv.addObject("msg","success");
			}else{
				mv.addObject("msg","failed");
			}
			mv.setViewName("save_result");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		return mv;
	}
	
	/**
	 * 修改通讯录
	 */
	@RequestMapping(value="/update")
	public ModelAndView editU(PrintWriter out) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			AddressLookBean bean = new AddressLookBean();
			Long id = Long.parseLong((String) pd.get("id"));
			bean.setId(id);
			bean.setUserName((String) pd.get("userName"));
			bean.setMobilePhone((String)pd.get("mobilePhone"));
			Integer sex = Integer.parseInt((String) pd.get("sex"));
			bean.setSex(sex);
			bean.setSurnameId(Long.parseLong((String) pd.get("surnameId")));
			addressLookService.updateAddressLook(bean);
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
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
			List<PageData>	userList = addressLookService.getAllAddressLookListPage(page);	//列出用户列表
			
			mv.setViewName("weizu/addressLook/list");
			mv.addObject("userList", userList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			AddressLookBean param = new AddressLookBean();
			param.setUserName((String)pd.get("userName"));
			List<AddressLookBean> exits = addressLookService.findAddressLookByCondition(param);
			if(exits != null && exits.size()>0){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			Long id = Long.parseLong((String) pd.get("id"));
			AddressLookBean param = new AddressLookBean();
			param.setId(id);
			addressLookService.deleteAddressLook(param);
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
