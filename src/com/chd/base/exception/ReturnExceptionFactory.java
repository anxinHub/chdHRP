/** 
* 2014-4-17 
* ReturnExceptionFactory.java 
* author:pengjin
*/ 
package com.chd.base.exception; 

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;


public class ReturnExceptionFactory {
	private static Logger logger = Logger.getLogger(ReturnExceptionFactory.class);
	
	public static String getException(String msg,Exception e){
		logger.error(msg, e);
		return "{\"error\":\""+e.getMessage()+"\"}";
	}
	
	public static String getException(String msg,DataAccessException e){
		logger.error(msg, e);
		return "{\"error\":\""+e.getMessage()+"\"}";
	}
	
}
