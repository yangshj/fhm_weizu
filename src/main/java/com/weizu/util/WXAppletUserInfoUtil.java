package com.weizu.util;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WXAppletUserInfoUtil {

	private static Logger log = LoggerFactory.getLogger(WXAppletUserInfoUtil.class);

	/**
	 * 获取微信小程序 session_key 和 openid
	 * 
	 * @author zhy
	 * @param code
	 *            调用微信登陆返回的Code
	 * @return
	 */
	public static JSONObject getSessionKeyOropenid(String code) {
		// 微信端登录code值
		String wxCode = code;
		ResourceBundle resource = ResourceBundle.getBundle("weixin"); // 读取属性文件
		String requestUrl = resource.getString("url"); // 请求地址
														// https://api.weixin.qq.com/sns/jscode2session
		Map<String, String> requestUrlParam = new HashMap<String, String>();
		requestUrlParam.put("appid", resource.getString("appId")); // 开发者设置中的appId
		requestUrlParam.put("secret", resource.getString("appSecret")); // 开发者设置中的appSecret
		requestUrlParam.put("js_code", wxCode); // 小程序调用wx.login返回的code
		requestUrlParam.put("grant_type", "authorization_code"); // 默认参数

		// 发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session
		// 接口获取openid用户唯一标识
		JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost(requestUrl, requestUrlParam));
		return jsonObject;
	}

	/**
	 * 解密用户敏感数据获取用户信息
	 * 
	 * @author zhy
	 * @param sessionKey
	 *            数据进行加密签名的密钥
	 * @param encryptedData
	 *            包括敏感数据在内的完整用户信息的加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @return
	 */
	public static JSONObject getUserInfo(String encryptedData,
			String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decode(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base
						+ (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters
					.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return JSON.parseObject(result);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
