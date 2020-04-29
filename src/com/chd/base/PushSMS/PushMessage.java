package com.chd.base.PushSMS;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.DateUtil;

public class PushMessage {

	private static Logger logger = Logger.getLogger(PushMessage.class);

	static String sendTime = DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS");

	private PushMessage() {
	}

	private static volatile PushMessage instance = null;

	public static PushMessage getInstance() {
		if (instance == null) {
			synchronized (PushMessage.class) {
				if (instance == null) {
					instance = new PushMessage();
				}
			}
		}
		return instance;
	}

	public static String sendContent(Map<String, Object> sendMap) {
		
		try {

			String urlStr = sendMap.get("Url").toString();

			HttpClient myclient = new HttpClient(); // 构建http客户端
			
			myclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");  

			PostMethod mypost = new PostMethod(urlStr); // 加密端口

			mypost.addParameter("PhoneNum", sendMap.get("PhoneNum").toString());

			mypost.addParameter("Content", sendMap.get("Content").toString());

			myclient.executeMethod(mypost); // 获得http返回码
			
			Thread.sleep(100);
			
			byte[] postResult = mypost.getResponseBody();

			logger.debug("返回数据 = " + postResult.toString());

			mypost.releaseConnection(); // 释放http连接

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

	public Map<String, Object> send(Map<String, Object> sendMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();//如果需要扩展 返回一些参数使用
		
		Map<String, String> configMap = getConfigData();
		
		sendMap.put("Url", configMap.get("URL"));
		
		String phoneNum = sendMap.get("PhoneNum").toString();
		
		String content = sendMap.get("Content").toString();
		
		if(StringUtils.isNotEmpty(phoneNum) && StringUtils.isNotEmpty(content)){
			
			sendContent(sendMap);
			
		}
		

		return reMap;
	}

	

	public static void main(String[] args) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("Url", "http://192.168.100.130/prmInterface/prmInterfaceHIS2008.asmx/SendMessage");
		
		map.put("PhoneNum", "13126611727");
		
		map.put("Content", "供应商平台发送短信测试");
		
		PushMessage pm = new PushMessage();
		
		pm.getInstance().send(map);

	}
	
	public static Map<String, String> getConfigData() {

		Map<String, String> map = new HashMap<String, String>();

		String url = LoadSystemInfo.getProjectUrl();

		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
		
		File xmlFile = new File(url+"WEB-INF\\pushsms\\config.xml");// 根据指定的路径创建file对象

		Document document; 
		
		try {
			document = sax.read(xmlFile);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
			
			Element root = document.getRootElement();// 获取根节点

			List<Element> childList = root.elements();

			for (int i = 0; i < childList.size(); i++) {
				
				Element e = (Element) childList.get(i);

				map.put(e.getName(), e.getData().toString().trim());
				
			}

		} catch (DocumentException e) {
			
			e.printStackTrace();
			
		}
		
		return map;

	}

}
