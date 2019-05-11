package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.weizu.common.enums.IntegralModuleEnum;
import com.weizu.common.enums.IntegralOperTypeEnum;
import com.weizu.helper.ResultHelper;
import com.weizu.helper.UserOpenInfo;
import com.weizu.helper.WeChatAppHelper;
import com.weizu.helper.WeiXinMemoryCacheHelper;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.GetIntegralRE;
import com.weizu.pojo.integral.IntegralBean;
import com.weizu.pojo.integral.IntegralRecordBean;
import com.weizu.service.addressLockk.UserInfoService;
import com.weizu.service.integral.IntegralRecordService;
import com.weizu.service.integral.IntegralService;
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
 * Description: 积分
 *
 * @since : 2019/3/26 15:26:45
 **/
@Controller
@RequestMapping(value="/weizu/weixin/integral")
public class WeiXinIntegralController extends BaseController {

    @Autowired
    private IntegralService integralService;
    @Autowired
    private IntegralRecordService integralRecordService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 我的积分
     */
    @RequestMapping(value="/getIntegral")
    @ResponseBody
    public void getIntegral(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetIntegralRE re = new GetIntegralRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String module = request.getParameter("module");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                IntegralBean query = new IntegralBean();
                query.setAppId(weChatAPPBean.getId());
                query.setModule(Integer.parseInt(module));
                query.setUserId(userOpenInfo.getUserId());
                List<IntegralBean> list = integralService.getIntegralByCondition(query);
                if(list!=null && list.size()>0){
                    re.setIntegralBean(list.get(0));
                    List<IntegralRecordBean>  listRecord = integralRecordService.getIntegralRecordByIntegralId(list.get(0).getId());
                    re.setList(listRecord);
                } else {
                    UserInfoBean user = userInfoService.findUserById(userOpenInfo.getUserId().toString());
                    IntegralBean bean = new IntegralBean();
                    bean.setUserId(userOpenInfo.getUserId());
                    bean.setAppId(weChatAPPBean.getId());
                    bean.setModule(IntegralModuleEnum.SYSTEM.getIndex());
                    bean.setNickName(user.getNickName());
                    bean.setIntegral(0);
                    integralService.insertIntegral(bean);
                    re.setIntegralBean(bean);
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

    /**
     * 新增积分
     */
    @RequestMapping(value="/addIntegral")
    @ResponseBody
    public void addIntegral(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetIntegralRE re = new GetIntegralRE();
        try {
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            re.setResult(ResultHelper.FAIL);
            String sessionId = request.getParameter("sessionId");
            String appId = request.getParameter("appId");
            String module = request.getParameter("module");
            String number = request.getParameter("number");
            String remark = request.getParameter("remark");
            UserOpenInfo userOpenInfo = WeiXinMemoryCacheHelper.getOpenidBySessionId(sessionId);
            if(userOpenInfo!=null){
                WeChatAPPBean weChatAPPBean = WeChatAppHelper.getWeChatApp(appId);
                if(weChatAPPBean==null){
                    re.setResult(ResultHelper.FAIL);
                    re.setMsg("无效的appId");
                    return;
                }
                IntegralBean query = new IntegralBean();
                query.setAppId(weChatAPPBean.getId());
                query.setModule(Integer.parseInt(module));
                query.setUserId(userOpenInfo.getUserId());
                List<IntegralBean> list = integralService.getIntegralByCondition(query);
                if(list!=null && list.size()>0){
                    IntegralBean exits = list.get(0);
                    // 更新积分
                    re.setIntegralBean(exits);
                    exits.setIntegral(exits.getIntegral()+Integer.parseInt(number));
                    integralService.updateIntegral(exits);
                    // 插入积分记录
                    IntegralRecordBean item = new IntegralRecordBean();
                    item.setIntegral(Integer.parseInt(number));
                    item.setOperType(IntegralOperTypeEnum.ADD.getIndex());
                    item.setIntegralId(exits.getId());
                    item.setRemark(remark);
                    integralRecordService.insertIntegralRecord(item);
                    // 获取积分
                    List<IntegralRecordBean>  listRecord = integralRecordService.getIntegralRecordByIntegralId(list.get(0).getId());
                    re.setList(listRecord);
                    re.setIntegralBean(exits);
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
