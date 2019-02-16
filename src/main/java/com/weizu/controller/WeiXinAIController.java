package com.weizu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.weizu.helper.BaiduAuthServiceHelper;
import com.weizu.helper.ResultHelper;
import com.weizu.pojo.PlantRecognitionRE;
import com.weizu.pojo.SaveUserInfoRE;
import com.weizu.util.Base64Util;
import com.weizu.util.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@Controller
@RequestMapping(value="/weizu/weixin")
public class WeiXinAIController extends BaseController {

    // 植物识别请求url
    String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";


    @RequestMapping("plantRecognition")
    @ResponseBody
    public void uploadHeadImage(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "imgFile" , required=false) MultipartFile imageFile) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PlantRecognitionRE re = new PlantRecognitionRE();
        try {
            re.setResult(ResultHelper.FAIL);
            // 图片base64
            byte[] imgData = imageFile.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 获取token
            String accessToken = BaiduAuthServiceHelper.getAuth();
            // 图片识别
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject object = JSON.parseObject(result);
            JSONArray jsonArray = object.getJSONArray("result");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String score = jsonObject.getString("score");
            String name = jsonObject.getString("name");
            re.setName(name);
            re.setScore(score);
            re.setResult(ResultHelper.SUCCESS);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(re));
            writer.flush();
        }
    }

}
