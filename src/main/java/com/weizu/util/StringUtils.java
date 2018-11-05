package com.weizu.util;

/**
 * 字符串工具类
 * @author liuhongbin
 */
public final class StringUtils {
	/**
	 * 判断是否为空串（是返回true）
	 * 
	 * @param str 待处理字符串
	 * @param trim 是否首尾去空
	 * @return 当字符串为空时返回true，否则返回false
	 * @author liuhongbin
	 */
	public static boolean isEmpty(String str, boolean trim) {
		String value = str;
		if (value == null) {
			return true;
		}

		if (trim) {
			value = value.trim();
		}

		if ("".equalsIgnoreCase(value)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为非空串（是返回false）
	 * 
	 * @param str 待处理字符串
	 * @param trim 是否首尾去空
	 * @return 当字符串为空时返回false，否则返回true
	 * @author liuhongbin
	 */
	public static boolean isNotEmpty(String str, boolean trim) {
		return !isEmpty(str, trim);
	}

	public static String valueOf(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<bytes.length; i++){
			s.append(bytes[i]);
			if(i != bytes.length-1){
				s.append(",");
			}
		}
		return s.toString();
	}

	public static String[] spit(String str){
		if(null == str || StringUtils.isEmpty(str, true)){
			return null;
		}
		String split = ",";
		if(str.startsWith("[") && str.endsWith("]")){
			str = str.substring(1, str.length()-1);
			split = ", ";
		}
		return str.split(split);
	}
	public static String spell(String... array){
		if(null == array || 0 == array.length){
			return null;
		}
		if(1 == array.length){
			return array[0];
		}
		StringBuilder str = new StringBuilder();
		for(int i=0; i<array.length; i++){
			str.append(array[i]);
			if(i != array.length-1){
				str.append(",");
			}
		}
		return str.toString();
	}
}
