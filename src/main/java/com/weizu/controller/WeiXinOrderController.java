package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.common.enums.ExchangeStatusEnum;
import com.weizu.common.enums.IntegralOperTypeEnum;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.*;
import com.weizu.service.addressLockk.UserInfoService;
import com.weizu.service.integral.CommodityService;
import com.weizu.service.integral.IntegralRecordService;
import com.weizu.service.integral.IntegralService;
import com.weizu.service.integral.OrderService;
import com.weizu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Description: 订单
 *
 * @since : 2019/3/26 15:26:45
 **/
@Controller
@RequestMapping(value="/weizu/weixin/order")
public class WeiXinOrderController  extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private IntegralRecordService integralRecordService;

    /**
     * 兑换
     */
    @RequestMapping(value="/exchange")
    @ResponseBody
    public void exchange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ExchangeRE re = new ExchangeRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String commodityId = request.getParameter("commodityId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                UserInfoBean userInfoBean = userInfoService.findUserById(userOpenInfo.getUserId().toString());
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                if(StringUtil.isEmpty(commodityId)){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的商品");
                    return;
                }
                IntegralBean query = new IntegralBean();
                query.setUserId(userOpenInfo.getUserId());
                query.setAppId(weChatAPPBean.getId());
                List<IntegralBean> list = integralService.getIntegralByCondition(query);
                if(list==null || list.size()==0){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("积分不足");
                    return;
                }
                IntegralBean integralBean = list.get(0);
                CommodityBean commodityBean = commodityService.findCommodityById(Long.parseLong(commodityId));
                if(commodityBean.getIntegralNum()>integralBean.getIntegral()){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("积分不足");
                    return;
                }
                // 插入订单
                OrderBean orderBean = new OrderBean();
                orderBean.setCommodityId(commodityBean.getId());
                orderBean.setCommodityNum(1);
                orderBean.setCommodityTitle(commodityBean.getTitle());
                orderBean.setNickName(userInfoBean.getNickName());
                orderBean.setExchangeStatus(ExchangeStatusEnum.NOT_HAVE.getIndex());
                orderBean.setIntegralNum(commodityBean.getIntegralNum());
                orderBean.setOrderNo(System.currentTimeMillis()+""+userOpenInfo.getUserId());
                orderBean.setUserId(userOpenInfo.getUserId());
                orderBean.setCreateTime(new Date());
                orderBean.setModifyTime(new Date());
                orderService.insertOrder(orderBean);
                // 更新积分
                IntegralBean update = new IntegralBean();
                update.setId(integralBean.getId());
                update.setIntegral(integralBean.getIntegral()-commodityBean.getIntegralNum());
                integralService.updateIntegral(update);
                // 插入积分消费记录
                IntegralRecordBean bean = new IntegralRecordBean();
                bean.setIntegralId(integralBean.getId());
                bean.setIntegral(commodityBean.getIntegralNum());
                bean.setOperType(IntegralOperTypeEnum.SUB.getIndex());
                bean.setCreateTime(new Date());
                bean.setRemark("购买商品"+commodityBean.getTitle()+"消费积分"+commodityBean.getIntegralNum());
                integralRecordService.insertIntegralRecord(bean);
                // 返回结果成功
                re.setResult(ResultHelper.SUCCESS);
                re.setOrderNo(orderBean.getOrderNo());
                re.setOrderId(orderBean.getId());
                OrderInfoBean param = new OrderInfoBean();
                param.setOrderNo(orderBean.getOrderNo());
                List<OrderInfoBean> orderInfoBeans= orderService.getOrderListByCondition(param);
                re.setOrderInfo(orderInfoBeans.get(0));
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
     * 订单详情
     */
    @RequestMapping(value="/orderDetail")
    @ResponseBody
    public void orderDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderDetailRE re = new OrderDetailRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String orderNo = request.getParameter("orderNo");
            String orderId = request.getParameter("orderId");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                OrderInfoBean param = new OrderInfoBean();
                param.setOrderNo(orderNo);
                List<OrderInfoBean> orderInfoBeans= orderService.getOrderListByCondition(param);
                re.setOrderInfo(orderInfoBeans.get(0));
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

    /**
     * 订单完成
     */
    @RequestMapping(value="/orderFinish")
    @ResponseBody
    public void orderFinish(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderDetailRE re = new OrderDetailRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String orderNo = request.getParameter("orderNo");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                OrderBean query = new OrderBean();
                query.setOrderNo(orderNo);
                List<OrderBean> list = orderService.getOrderByCondition(query);
                if(list==null || list.size()==0){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的订单id");
                    return;
                }
                OrderBean orderBean = list.get(0);
                if(orderBean.getExchangeStatus()!=null && orderBean.getExchangeStatus().equals(ExchangeStatusEnum.ALREADY.getIndex())){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("订单已兑换");
                    return;
                }
                OrderBean update = new OrderBean();
                update.setId(orderBean.getId());
                update.setExchangeEmp(userOpenInfo.getUserId());
                update.setExchangeStatus(ExchangeStatusEnum.ALREADY.getIndex());
                update.setExchangeTime(new Date());
                orderService.updateOrder(update);
                OrderInfoBean param = new OrderInfoBean();
                param.setOrderNo(orderNo);
                List<OrderInfoBean> orderInfoBeans= orderService.getOrderListByCondition(param);
                re.setOrderInfo(orderInfoBeans.get(0));
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


    /**
     * 我的订单列表
     */
    @RequestMapping(value="/myOrderList")
    @ResponseBody
    public void myOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MyOrderListRE re = new MyOrderListRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String exchangeStatus = request.getParameter("exchangeStatus");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                OrderInfoBean query = new OrderInfoBean();
                query.setUserId(userOpenInfo.getUserId());
                if(StringUtil.isNotEmpty(exchangeStatus)){
                    query.setExchangeStatus(Integer.parseInt(exchangeStatus));
                }
                List<OrderInfoBean> list = orderService.getOrderListByCondition(query);
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

}
