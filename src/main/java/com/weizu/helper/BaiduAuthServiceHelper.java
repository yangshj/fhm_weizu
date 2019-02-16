package com.weizu.helper;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weizu.core.queue.ITimeOutMap;
import com.weizu.core.queue.TimeOutForCreateDateMap;
import com.weizu.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 百度token获取工具
 * 百度token有效时间通常是一个月，这里缓存10天
 */
public class BaiduAuthServiceHelper {

    private static String  clientId = "5gvknFcqlxLQX5WAR5u6x52s";
    private static String clientSecret = "N9ams5chQIug12jPLN2t3YCmcHIGKHW5";
    private static String ACCESSTOKEN = "accessToken";

    // 百度token缓存; key=accessToken ，value=accessToken
    private static ITimeOutMap<String, String> sessionOpenIdMap = new TimeOutForCreateDateMap<String, String>();

    static {
        sessionOpenIdMap.setTimeOut(10 * 24 * 60 * 60 * 1000); // 10天
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
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        String token = sessionOpenIdMap.get(ACCESSTOKEN);
        if(StringUtil.isNotEmpty(token)){
            return token;
        } else {
            String accessToken = getAuth(clientId, clientSecret);
            sessionOpenIdMap.put(ACCESSTOKEN, accessToken);
            return accessToken;
        }
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject json = JSON.parseObject(result);
            String access_token = json.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
