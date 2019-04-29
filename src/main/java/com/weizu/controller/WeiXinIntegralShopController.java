package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.common.enums.ModuleEnum;
import com.weizu.common.enums.StatusEnum;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.CommodityBean;
import com.weizu.pojo.integral.LoadMoreCommodityRE;
import com.weizu.pojo.oa.BaseRE;
import com.weizu.pojo.other.ImageTextBean;
import com.weizu.pojo.other.LoadMoreImageTextRE;
import com.weizu.service.integral.CommodityService;
import com.weizu.service.other.ImageTextService;
import com.weizu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Description: 其它相关
 *
 * @since : 2019/3/26 15:26:45
 **/
@Controller
@RequestMapping(value="/weizu/weixin/commodity")
public class WeiXinIntegralShopController extends BaseController {

    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value="/loadMoreCommodity")
    @ResponseBody
    public void loadMoreCommodity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoadMoreCommodityRE re = new LoadMoreCommodityRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String startLimit = request.getParameter("startLimit");
            String endLimit = request.getParameter("endLimit");
            String userId = request.getParameter("userId");
            String module = request.getParameter("module");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                CommodityBean query = new CommodityBean();
                query.setStartLimit(Integer.parseInt(startLimit));
                query.setEndLimit(Integer.parseInt(endLimit));
                query.setAppId(weChatAPPBean.getId());
                query.setStatus(StatusEnum.EFFECTIVE.getIndex());
                if(StringUtil.isNotEmpty(module)){
                    query.setModule(Integer.parseInt(module));
                }
                // 判断不能去，和我的发布公用一个方法
                if(StringUtil.isNotEmpty(userId)){
                    query.setUserId(userOpenInfo.getUserId());
                    // 积分商城是个独立模块--需要单独过滤
                    query.setModule(ModuleEnum.INTEGRAL_SHOP.getIndex());
                }
                List<CommodityBean>  list = commodityService.loadMoreByCondition(query);
                for(CommodityBean bean:list){
                    if(StringUtil.isNotEmpty(bean.getTitle()) && bean.getTitle().length()>20){
                        bean.setTitleAb(bean.getTitle().substring(0,15)+"...");
                    } else {
                        bean.setTitleAb(bean.getTitle());
                    }
                    if(StringUtil.isNotEmpty(bean.getContent()) && bean.getContent().length()>20){
                        bean.setContentAb(bean.getContent().substring(0,25)+"...");
                    } else {
                        bean.setContentAb(bean.getContent());
                    }
                }
                re.setList(list);
                re.setResult(ResultHelper.SUCCESS);
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


    @RequestMapping(value="/submitCommodity")
    @ResponseBody
    public void submitCommodity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseRE re = new BaseRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String integralNum = request.getParameter("integralNum");
            String commodityNum = request.getParameter("commodityNum");
            String module = request.getParameter("module");
            String cloudPathArrayStr = request.getParameter("cloudPathArrayStr");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                CommodityBean bean = new CommodityBean();
                bean.setTitle(title);
                bean.setContent(content);
                bean.setIntegralNum(Integer.parseInt(integralNum));
                bean.setCommodityNum(Integer.parseInt(commodityNum));
                bean.setUserId(userOpenInfo.getUserId());
                if(StringUtil.isNotEmpty(cloudPathArrayStr)){
                    bean.setCloudPath(cloudPathArrayStr);
                    String[] cloudPathArray = cloudPathArrayStr.split(",");
                    bean.setFirstPath(cloudPathArray[0]);
                }
                if(StringUtil.isNotEmpty(module)){
                    bean.setModule(Integer.parseInt(module));
                } else {
                    // 默认为校园一角
                    bean.setModule(ModuleEnum.SCHOOL.getIndex());
                }
                bean.setAppId(weChatAPPBean.getId());
                bean.setStatus(StatusEnum.EFFECTIVE.getIndex());
                commodityService.insertCommodity(bean);
                re.setResult(ResultHelper.SUCCESS);
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

    @RequestMapping(value="/deleteCommodity")
    @ResponseBody
    public void deleteCommodity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseRE re = new BaseRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String id = request.getParameter("id");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                if(StringUtil.isNotEmpty(id)){
                    commodityService.deleteCommodityLogic(Long.parseLong(id));
                }
                re.setResult(ResultHelper.SUCCESS);
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

}
