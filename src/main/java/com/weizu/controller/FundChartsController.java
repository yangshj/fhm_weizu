package com.weizu.controller;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;
import com.weizu.pojo.fund.FundNetWorthBean;
import com.weizu.service.fund.FundNetWorthService;
import com.weizu.service.fund.FundService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/weizu/fundCharts")
public class FundChartsController extends BaseController {
    String menuUrl = "weizu/fundCharts/list.do";
    @Autowired
    private FundNetWorthService fundNetWorthService;
    @Autowired
    private FundService fundService;

    /**
     * 显示列表
     */
    @RequestMapping(value="/list")
    public ModelAndView listUsers(Page page){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            List<FundBean> fundList = fundService.findFundByCondition(null);
            String fundId = pd.getString("fundId");
            FundNetWorthBean param = new FundNetWorthBean();
            if(null != fundId && !"".equals(fundId)){
                fundId = fundId.trim();
            } else {
                fundId = fundList.get(0).getId().toString();
            }
            pd.put("fundId", fundId);
            param.setFundId(Long.parseLong(fundId));
            //列出所有列表
            List<FundNetWorthBean>	itemList = fundNetWorthService.findAllFundNetWorthByCondition(param);
            // 数据转换
            List<List<String>> dataList = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
            for(FundNetWorthBean item: itemList){
                List<String> dataItemList =  new ArrayList<>();
                Date worthDate = format.parse(item.getWorthDate());
                dataItemList.add(format2.format(worthDate));
                if(item.getNetWorth()!=null){
                    dataItemList.add(item.getNetWorth().toString());
                }else{
                    continue;
                }
                dataList.add(dataItemList);
            }
            mv.setViewName("weizu/fundCharts/list");
            mv.addObject("dataList", dataList);
            mv.addObject("fundList",fundList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }


}
