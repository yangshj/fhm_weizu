package com.weizu.controller;

import com.weizu.util.FileUtil;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

import java.util.HashMap;

public class Sample {

    //设置APPID/AK/SK
    public static final String APP_ID = "15574497";
    public static final String API_KEY = "9x62l6wA5QOxm66iSobzt6WO";
    public static final String SECRET_KEY = "MpaoUToTLO3GYclhRGlkiIaV0FUlKbXn";

    /**
     * 通用文字识别
     */
    public static void basicGeneral() {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "C:/Users/lenovo/Desktop/captcha.jpg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }

    /**
     * 网络图片文字识别
     */
    public static void webImage() {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_language", "true");


        // 参数为本地路径
        String image = "C:/Users/lenovo/Desktop/captcha.jpg";
        JSONObject res = client.webImage(image, options);
        System.out.println(res.toString(2));

//        // 参数为二进制数组
//        byte[] file = FileUtil.readFileByBytes("test.jpg");
//        res = client.webImage(file, options);
//        System.out.println(res.toString(2));
//
//        // 网络图片文字识别, 图片参数为远程url图片
//        JSONObject res = client.webImageUrl(url, options);
//        System.out.println(res.toString(2));

    }

    public static void main(String[] args) {
        webImage();
    }
}
