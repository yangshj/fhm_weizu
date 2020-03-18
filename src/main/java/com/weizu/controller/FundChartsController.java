package com.weizu.controller;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;
import com.weizu.pojo.fund.FundNetWorthBean;
import com.weizu.service.fund.FundNetWorthService;
import com.weizu.service.fund.FundService;
import com.weizu.util.StringUtil;
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
            Date today = new Date();
            List lastMonthList = getItemList(itemList, new Date(today.getTime()-30*24*60*60*1000L));
            List lastThreeList = getItemList(itemList, new Date(today.getTime()-90*24*60*60*1000L));
            List lastSixList = getItemList(itemList, new Date(today.getTime()-180*24*60*60*1000L));
            List lastYearList = getItemList(itemList, new Date(today.getTime()-360*24*60*60*1000L));
            List lastThreeYearList = getItemList(itemList, new Date(today.getTime()-3*360*24*60*60*1000L));
            mv.addObject("lastMonthList", getDataList(lastMonthList));
            mv.addObject("lastThreeList", getDataList(lastThreeList));
            mv.addObject("lastSixList", getDataList(lastSixList));
            mv.addObject("lastYearList", getDataList(lastYearList));
            mv.addObject("lastThreeYearList", getDataList(lastThreeYearList));
            mv.addObject("fundList",fundList);
            mv.addObject("lastNetWorth",itemList.get(itemList.size()-1));
            mv.addObject("fund",getFundBeanById(fundList, Long.parseLong(fundId)));
            mv.addObject("pd", pd);
            mv.setViewName("weizu/fundCharts/list");
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    private FundBean getFundBeanById(List<FundBean> fundList, Long fundId){
        if(fundList==null || fundList.size()==0){
            return null;
        }
        for(FundBean bean : fundList){
            if(bean.getId().equals(fundId)){
                return bean;
            }
        }
        return null;
    }

    /**
     * 获取指定最近时间的list, 不破坏原先对象
     * @param lastTime 指定的最近时间
     */
    private List<FundNetWorthBean> getItemList(List<FundNetWorthBean>itemList, Date lastTime){
        if(itemList==null || itemList.size()==0){
            return null;
        }
        List<FundNetWorthBean> resultList = new ArrayList<>();
        resultList.addAll(itemList);
        // 先排序
        Collections.sort(resultList, new Comparator<FundNetWorthBean>(){
            @Override
            public int compare(FundNetWorthBean o1, FundNetWorthBean o2) {
                return o1.getWorthDate().compareTo(o2.getWorthDate());
            }
        });
        // 筛选满足时间的数据
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String lastTimeStr = format.format(lastTime);
        Iterator<FundNetWorthBean> iterator = resultList.iterator();
        while(iterator.hasNext()){
            FundNetWorthBean item = iterator.next();
            if(StringUtil.isEmpty(item.getWorthDate())){
                iterator.remove();
                continue;
            }
            if(item.getWorthDate().compareTo(lastTimeStr)<=0){
                iterator.remove();
            }

        }
        return resultList;
    }

    /**
     * 1、将数据库对象转换成页面需要的格式
     * 2、返回如下格式数据：
     * [[20150120, 1.0000], [20150123, 1.0332], [20150126, 1.0416]]
     */
    private List<List<String>> getDataList(List<FundNetWorthBean> itemList) throws Exception{
        if(itemList==null || itemList.size()==0){
            return null;
        }
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
        return dataList;
    }


    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }


}

