package com.chd.base.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 
 * @ClassName: IdGen 
 * @Description: ID生成工具类
 * @author zn 
 * @date 2017年11月2日 上午10:41:43 
 * 
 *
 */
public class IdGen {
	private static SecureRandom random = new SecureRandom();
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return encodeBase62(randomBytes);
	}
	
	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
	}
}
