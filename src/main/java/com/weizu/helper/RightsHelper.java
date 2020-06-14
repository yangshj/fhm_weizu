package com.weizu.helper;

import com.fh.util.Tools;
import com.weizu.pojo.addressBook.SurNameBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 权限计算帮助类
 */
public class RightsHelper {
	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * @param rights int型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(int[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(rights[i]);
		}
		return num;
	}
	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * @param rights String型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(String[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(Integer.parseInt(rights[i]));
		}
		return num;
	}

	public static BigInteger sumRights(List<String> rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.size(); i++){
			num = num.setBit(Integer.parseInt(rights.get(i)));
		}
		return num;
	}

	public static BigInteger getRights(int rights){
		BigInteger num = new BigInteger("0");
		num = num.setBit(rights);
		return num;
	}
	
	/**
	 * 测试是否具有指定编码的权限
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(BigInteger sum,int targetRights){
		return sum.testBit(targetRights);
	}
	
	/**
	 * 测试是否具有指定编码的权限
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(String sum,int targetRights){
		if(Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum),targetRights);
	}
	
	/**
	 * 测试是否具有指定编码的权限
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(String sum,String targetRights){
		if(Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum),targetRights);
	}
	
	/**
	 * 测试是否具有指定编码的权限
	 * @param sum
	 * @param targetRights
	 * @return
	 */
	public static boolean testRights(BigInteger sum,String targetRights){
		return testRights(sum,Integer.parseInt(targetRights));
	}

	/**
	 * 根据经纬度判断是否有权限
	 * @return
	 */
	public static boolean hasRights(WeChatAPPBean weChatAPPBean, Double latitude, Double longitude){
		if(weChatAPPBean==null){
			return false;
		}
		if(weChatAPPBean.getName().equals("西玉曹")){
			return hasRights_XYC(latitude, longitude);
		} else if(weChatAPPBean.getName().equals("章里集")){
			return hasRights_ZLj(latitude, longitude);
		}
		return false;
	}

	/**
	 * 根据经纬度判断是否有权限--西玉曹
	 * 114.469212,36.387543  东
	 * 114.436865,36.389106  西
	 * 114.459148,36.368149  南
	 * 114.457636,36.398847  北
	 * @return
	 */
	private static boolean hasRights_XYC(Double latitude, Double longitude){
		// 在此经纬度范围内，则有权限
		if(longitude>114.436865 && longitude<114.469212 && latitude>36.368149 && latitude<36.398847){
			return true;
		}
		return false;
	}

	/**
	 * 根据经纬度判断是否有权限--章里集
	 * 114.511428,36.379393  东
	 * 114.489284,36.381881  西
	 * 114.500614,36.369304  南
	 * 114.498725,36.390173  北
	 * @return
	 */
	private static boolean hasRights_ZLj(Double latitude, Double longitude){
		// 在此经纬度范围内，则有权限
		if(longitude>114.489284 && longitude<114.511428 && latitude>36.369304 && latitude<36.390173){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String str = "1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,223,224,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311";
		List<String> rightList = new ArrayList<String>();
		str.split(",");
		for(String id : str.split(",")){
			rightList.add(id);
		}
		BigInteger rightString = RightsHelper.sumRights(rightList);
		System.out.println(rightString);
	}
}
