package com.weizu.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.ServiceHelper;
import com.weizu.common.enums.MiniProgramStateEnum;
import com.weizu.pojo.addressBook.UserInfoBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.other.ImageTextBean;
import com.weizu.service.addressLockk.UserInfoService;
import com.weizu.service.addressLockk.WeChatAPPService;
import com.weizu.util.HttpUtil;
import com.weizu.util.StringUtil;
import com.weizu.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用于发送订阅消息
 */
public class SubscribeMessageHelper {

    private static final String tempId = "B_dQ-EKgCSVoKLDxljRJRwyE9dJa1ba-nh_qWuEooc0";

    private static UserInfoService userInfoService;
    /**
     * 异步发送消息
     */
    public static void sendMessageAsync(final ImageTextBean bean, final WeChatAPPBean weChatAPP){
        Thread t = new Thread(){
            @Override
            public void run() {
                sendMessage(bean, weChatAPP);
            }
        };
        t.start();
    }


    /**
     * 同步发送消息
     */
    public static void sendMessage(ImageTextBean bean, WeChatAPPBean weChatAPP){
        if(bean==null || bean.getAppId()==null){
            System.out.println("发送订阅消息失败缺失参数："+ JSON.toJSONString(bean));
            return;
        }
        try {
            if(userInfoService==null){
                userInfoService = (UserInfoService) ServiceHelper.getService("userInfoServiceImpl");
            }
            UserInfoBean param = new UserInfoBean();
            param.setAppId(weChatAPP.getId());
            param.setSubscriptionsNotNull(true);
            List<UserInfoBean> userList = userInfoService.getAllUserByCondition(param);
            for(UserInfoBean user : userList){
                if(StringUtil.isEmpty(user.getSubscriptions())){
                    continue;
                }
                if(StringUtil.isEmpty(user.getOpenId())){
                    continue;
                }
                System.out.println(user.getSubscriptions());
                JSONObject json = (JSONObject) JSON.parseObject(user.getSubscriptions());
                String value = json.getString(tempId);
                if(value!=null && value.equals("accept")){
                    String token = WeChatAccessTokenHelper.getToken(weChatAPP.getAppId(), weChatAPP.getAppSecret());
                    sendHttp(token, user.getOpenId(), bean);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String sendHttp(String token, String openId, ImageTextBean bean) {
        if(StringUtil.isEmpty(openId)){
            System.out.println("openId不能为空"+JSON.toJSON(bean));
            return null;
        }
        if(StringUtil.isEmpty(token)){
            System.out.println("token不能为空");
            return null;
        }
        if(bean.getProgramState()==null || MiniProgramStateEnum.getEnumByIndex(bean.getProgramState())==null){
            System.out.println("订阅环境不能为空");
            return null;
        }
        System.out.println("开始发送订阅消息:"+JSON.toJSONString(bean));
        // 获取token地址
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            UserInfoBean user = userInfoService.findUserById(bean.getUserId().toString());
            if(StringUtil.isEmpty(user.getNickName())){
                System.out.println("发送人昵称不能为空");
                return null;
            }
            MiniProgramStateEnum programState = MiniProgramStateEnum.getEnumByIndex(bean.getProgramState());
            Template template=new Template();
            template.setTemplate_id(tempId);
            template.setTouser(openId);
            template.setProgramState(programState.getEnglish());
            template.setPage("/pages/xyyj/detail/index?share=true&id="+ bean.getId());
            List<TemplateParam> paras=new ArrayList<TemplateParam>();
            paras.add(new TemplateParam("name1",user.getNickName()));
            paras.add(new TemplateParam("time2",format.format(new Date())));
            paras.add(new TemplateParam("thing3",bean.getTitle()));
            paras.add(new TemplateParam("thing4",bean.getContent()));
            template.setTemplateParamList(paras);
            System.out.println(template.toJSON());
            HttpUtil.post(url, token, template.toJSON());
        } catch (Exception e) {
            System.err.printf("发送订阅消息失败！");
            e.printStackTrace();
        }
        return null;
    }


}
