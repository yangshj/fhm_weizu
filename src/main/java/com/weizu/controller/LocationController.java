package com.weizu.controller;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.weizu.service.addressLockk.UserLocationService;
import com.weizu.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/weizu/location")
public class LocationController  extends BaseController {

    @Autowired
    private UserLocationService userLocationService;

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
            List<PageData> locationList = userLocationService.getLocationListPage(page);
            for(PageData pageData : locationList){
                String url = (String) pageData.get("avatarUrl");
                if(StringUtil.isNotEmpty(url)){
                    int end = url.lastIndexOf("/");
                    String temp = url.substring(0, end);
                    System.out.println("temp: "+temp);
                    pageData.put("avatarUrl_46", temp+"/46");
                    pageData.put("avatarUrl_0", temp+"/0");
                }
            }
            mv.setViewName("weizu/location/list");
            mv.addObject("locationList", locationList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
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
