package com.weizu.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		return uuidStr;
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}

}
