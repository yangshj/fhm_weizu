package com.weizu.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.weizu.pojo.fund.FundBean;
import com.weizu.service.fund.FundNetWorthService;
import com.weizu.task.FundTask;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.weizu.service.fund.FundService;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;

@Controller
@RequestMapping(value = "/weizu/fund")
public class FundController extends BaseController {
    String menuUrl = "weizu/fund/list.do";
    @Autowired
    private FundService fundService;
    @Autowired
    private FundNetWorthService fundNetWorthService;

    /**
     * 新增基金信息
     */
    @RequestMapping(value="/save")
    public ModelAndView saveU(PrintWriter out) throws Exception{
        ModelAndView mv = this.getModelAndView();
        try{
            PageData pd = new PageData();
            pd = this.getPageData();
            FundBean param = new FundBean();
            param.setCode((String)pd.get("code"));
            List<FundBean> exits = fundService.findFundByCondition(param);
            if(exits==null || exits.size()==0){
                FundBean bean = new FundBean();
                bean.setCode((String)pd.get("code"));
                bean.setName((String)pd.get("name"));
                bean.setType((String)pd.get("type"));
                bean.setManager((String)pd.get("manager"));
                fundService.insertFund(bean);
                // 异步执行数据解析
                FundTask.asyncFundData(bean.getCode());
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
     * 修改信息
     */
    @RequestMapping(value="/update")
    public ModelAndView editU(PrintWriter out) throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
            FundBean bean = new FundBean();
            Long id = Long.parseLong((String) pd.get("id"));
            bean.setId(id);
            bean.setCode((String) pd.get("code"));
            bean.setName((String)pd.get("name"));
            bean.setType((String)pd.get("type"));
            bean.setManager((String)pd.get("manager"));
            fundService.updateFund(bean);
        }
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除信息
     */
    @RequestMapping(value="/deleteU")
    public void deleteU(PrintWriter out){
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            Long id = Long.parseLong((String) pd.get("id"));
            FundBean param = new FundBean();
            param.setId(id);
            fundService.deleteFund(param);
            fundNetWorthService.deleteFundNetWorthByFundId(id);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

    }

    /**
     * 显示列表
     */
    @RequestMapping(value="/list")
    public ModelAndView listUsers(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            String code = pd.getString("code");
            if(null != code && !"".equals(code)){
                code = code.trim();
                pd.put("code", code);
            }
            page.setPd(pd);
            List<PageData>	userList = fundService.getAllFundListPage(page);	//列出所有列表

            mv.setViewName("weizu/fund/list");
            mv.addObject("userList", userList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
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
            mv.setViewName("weizu/fund/edit");
            mv.addObject("path", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 判断编码是否存在
     */
    @RequestMapping(value="/hasU")
    @ResponseBody
    public Object hasU(){
        Map<String,String> map = new HashMap<String,String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            FundBean param = new FundBean();
            param.setCode((String)pd.get("code"));
            List<FundBean> exits = fundService.findFundByCondition(param);
            if(exits != null && exits.size()>0){
                errInfo = "error";
            }
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);				//返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value="/deleteAllU")
    @ResponseBody
    public Object deleteAllU() {
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String id = pd.getString("id");

            if(null != id && !"".equals(id)){
                String ArrayUSER_IDS[] = id.split(",");
                if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){fundService.deleteAllU(ArrayUSER_IDS);}
                pd.put("msg", "ok");
            }else{
                pd.put("msg", "no");
            }

            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }

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
                FundBean param = new FundBean();
                param.setId(id);
                FundBean bean = fundService.findFundById(param);
                mv.addObject("bean", bean);
            }
            mv.setViewName("weizu/fund/edit");
            mv.addObject("path", "update");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 立即同步
     */
    @RequestMapping(value="/synchro")
    @ResponseBody
    public Object synchro() {
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String code = pd.getString("code");

            if(null != code && !"".equals(code)){
                String[] ArrayCode = code.split(";");
                for(String codes : ArrayCode){
                    if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
                        FundTask.syncFundData(codes);
                    }
                    pd.put("msg", "ok");
                }
            }else{
                pd.put("msg", "no");
            }

            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }
}
