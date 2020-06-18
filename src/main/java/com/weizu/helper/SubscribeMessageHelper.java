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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            String params = "{" +
                    "  \"touser\": ,\" " +openId + "\","+
                    "  \"template_id\": " + tempId + "\","+
                    "  \"page\": \"/pages/xyyj/detail/index?share=true&id="+ bean.getId()+ "\"," +
                    "  \"miniprogram_state\":\" " + programState.getEnglish() + "\","+
                    "  \"lang\":\"zh_CN\"," +
                    "  \"data\": {" +
                    "       \"name1\": {" +
                    "          \"value\": \" " + user.getNickName() + "\","+
                    "      }," +
                    "      \"time2\": {" +
                    "          \"value\": \" " +format.format(new Date()) + "\","+
                    "      }," +
                    "      \"thing3\": {" +
                    "          \"value\": \" " +bean.getTitleAb() + "\","+
                    "      }," +
                    "      \"thing4\": {" +
                    "          \"value\": \" " + bean.getContentAb() + "\","+
                    "      } " +
                    "  }" +
                    "}";
            HttpUtil.post(url, token, params);
            System.out.println("开始发送订阅消息成功:"+JSON.toJSONString(params));
        } catch (Exception e) {
            System.err.printf("发送订阅消息失败！");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "{\"wLRnD9s2MU2Z1NW37Ky2_5UrQuXQb4yFEYQ7l3toqpg\":\"accept\",\"B_dQ-EKgCSVoKLDxljRJRxbLN3Ee49_arlAhGJWmRRs\":\"accept\"}";
        JSONObject json = (JSONObject) JSON.parseObject(str);
        String value = json.getString("B_dQ-EKgCSVoKLDxljRJRxbLN3Ee49_arlAhGJWmRRs");
        System.out.println("value:"+value);
    }
}
