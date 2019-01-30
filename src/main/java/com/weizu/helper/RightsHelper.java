package com.weizu.helper;

import com.fh.util.Tools;

import java.math.BigInteger;
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
	 * 114.465908,36.387993  东
	 * 114.443871,36.388321  西
	 * 114.449085,36.376574  南
	 * 114.457679,36.396129  北
	 * @return
	 */
	public static boolean hasRights(Double latitude, Double longitude){
		// 在此经纬度范围内，则有权限
		if(longitude>114.443871 && longitude<114.465908 && latitude>36.376574 && latitude<36.396129){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		double la = 36.384894;
		double lon = 114.461342;
		boolean right = hasRights(la,lon);
		System.out.println("right: "+right);
	}
}
