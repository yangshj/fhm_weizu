package com.weizu.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.TimeOutForCreateDateMap;
import com.weizu.util.HttpsClientUtil;
import com.weizu.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WeChatAccessTokenHelper {

    private static String  appId = "wx908e683643d04ebc";
    private static String secret = "e3ff4f4bceceb75355f52ede6ad95510";
    private static String ACCESS_TOKEN = "accessToken";

    // 百度token缓存; key=appId ，value=accessToken
    private static ITimeOutMap<String, String> sessionOpenIdMap = new TimeOutForCreateDateMap<String, String>();

    static {
        sessionOpenIdMap.setTimeOut(2 * 60 * 60 * 1000); // 2小时
        sessionOpenIdMap.setCustomTimeOutHandler(new ITimeOutMap.ITimeOutHandler<String, String>() {
            @Override
            public void timeOut(String key, String UserOpenId) {
                sessionOpenIdMap.remove(key);
            }
        });
    }

    /**
     * 获取权限token
     * @return 返回示例：
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    public static String getToken(String appId, String secret) {
        String token = sessionOpenIdMap.get(appId);
        if(StringUtil.isNotEmpty(token)){
            return token;
        } else {
            String accessToken = getAccessToken(appId, secret);
            sessionOpenIdMap.put(ACCESS_TOKEN, accessToken);
            return accessToken;
        }
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param appId - 小程序唯一凭证，即 AppID
     * @param secret - 小程序唯一凭证密钥
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAccessToken(String appId, String secret) {
        // 获取token地址
        String authHost = "https://api.weixin.qq.com/cgi-bin/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credential"
                // 2. 官网获取的 API Key
                + "&appid=" + appId
                // 3. 官网获取的 Secret Key
                + "&secret=" + secret;
        try {
            String result = HttpsClientUtil.sendRequest(getAccessTokenUrl, null, null);
            System.out.println("result:" + result);
            JSONObject json = JSON.parseObject(result);
            String accessToken = json.getString("access_token");
            return accessToken;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) {
        String token = getToken(appId,secret);
        System.out.println("token:"+token);
    }
}
