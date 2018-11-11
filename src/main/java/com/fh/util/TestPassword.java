package com.fh.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class TestPassword {
	public static void main(String[] args) {
		String passwd = new SimpleHash("SHA-1", "admin", "admin@123").toString();
		System.out.println(passwd);
	}
}
