/** 
* 2014-6-12 
* UUIDLong.java 
* author:pengjin
*/ 
package com.chd.base.util;

import java.util.UUID;

import com.chd.base.exception.SysException;

public class UUIDLong {
	private static UUIDLong _instance = new UUIDLong();

	private static long baseUUID = UUID.randomUUID().getMostSignificantBits();

	private UUIDLong() {
		baseUUID = UUID.randomUUID().getMostSignificantBits();
	}

	public static UUIDLong getInstance() {
		return _instance;
	}

	public static long longUUID() {
		baseUUID = UUID.randomUUID().getMostSignificantBits();
		return baseUUID;
	}

	public static String absStringUUID() {
		long r;
		do
			r = longUUID();
		while(r <=0L);
		if(String.valueOf(r).length()<15){
			throw new SysException("UUID获取失败，请稍候再试！");
		}
		return String.valueOf(r).substring(0,15);
	}
	
	
	public static long absLongUUID() {
		long r;
		do
			r = longUUID();
		while(r <= 0L);
		return r;
	}
}
