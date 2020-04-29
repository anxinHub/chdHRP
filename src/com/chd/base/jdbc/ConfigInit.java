/** 
* 2014-6-5 
* ConfigInit.java 
* author:pengjin
*/ 
package com.chd.base.jdbc; 

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.chd.base.startup.LoadSystemInfo;

public class ConfigInit {

	private static Logger logger = Logger.getLogger(ConfigInit.class);
	
	private static Properties system = new Properties();
	private static Properties ftp = new Properties();
	private static Properties config = new Properties();
	private static Properties transtypeconfig = new Properties();
	private static Properties processingcodeconfig = new Properties();
	private static Properties processingcoderesultsetconfig = new Properties();
	private static Properties processingcodedelconfig = new Properties();
	
	private final static String ftpUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/ftp.properties";
	
	private final static String configUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/config.properties";
	//消息码
	private final static String transtypeUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/transtype.properties";
	//交易码
	private final static String processingcodeUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/processingcode.properties";
	//返回结果集交易码 result set
	private final static String processingcoderesultsetUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/processingcode_resultset.properties";
	//返回需要删除医嘱的交易码 
	private final static String processingcoderdelUrl=LoadSystemInfo.getProjectUrl()+ "WEB-INF/resource/processingcode_del.properties";
	
	static{
		try{			
			InputStream is = new BufferedInputStream(new FileInputStream(LoadSystemInfo.getPropertiesUrl()));
			system.load(is);
			logger.debug("==================system.properties加载成功=====================");
			
			InputStream configIs = new BufferedInputStream(new FileInputStream(configUrl));
			config.load(configIs);
			logger.debug("==================config.properties加载成功=====================");
			InputStream configIs1 = new BufferedInputStream(new FileInputStream(transtypeUrl));
			transtypeconfig.load(configIs1);
			logger.debug("==================transtype.properties加载成功=====================");
			InputStream configIs2 = new BufferedInputStream(new FileInputStream(processingcodeUrl));
			processingcodeconfig.load(configIs2);
			logger.debug("==================processingcode.properties加载成功=====================");
			InputStream configIs3= new BufferedInputStream(new FileInputStream(processingcoderesultsetUrl));
			processingcoderesultsetconfig.load(configIs3);
			logger.debug("==================processingcode_resultset.properties加载成功=====================");
			InputStream configIs4= new BufferedInputStream(new FileInputStream(processingcoderesultsetUrl));
			processingcodedelconfig.load(configIs4);
			logger.debug("==================processingcode_del.properties加载成功=====================");
			InputStream ftpConfig= new BufferedInputStream(new FileInputStream(ftpUrl));
			ftp.load(ftpConfig);
			logger.debug("==================ftp.properties加载成功=====================");
			
		}catch( Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError( e );
		}
	}
	
	public static Properties getFtpConfig(){
		return ftp ;
	}

	public static Properties getConfiguration(){
		return system ;
	}
	public static Properties getTransType(){
		return transtypeconfig ;
	}
	public static Properties getProcessingCode(){
		return processingcodeconfig ;
	}
	public static Properties getProcessingCodeResultSet(){
		return processingcoderesultsetconfig ;
	}
	public static Properties getProcessingCodeDel(){
		return processingcodedelconfig ;
	}
	
	public static String getConfigProperties(String key){
		 
		return config.getProperty(key);
	}
	
	public static void setConfigProperties(String key,String val) throws IOException{
		 
	    OutputStream outputStream = new FileOutputStream(configUrl);  
	    config.setProperty(key, val);             
	    config.store(outputStream, "author: perry@e-tonggroup.com");  
	    outputStream.close();  
	       		
	}
}
