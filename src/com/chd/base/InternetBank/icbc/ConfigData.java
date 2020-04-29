package com.chd.base.InternetBank.icbc;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.chd.base.startup.LoadSystemInfo;

public class ConfigData {

	public static Map<String, String> getConfigData() {

		Map<String, String> map = new HashMap<String, String>();

		String url = LoadSystemInfo.getProjectUrl();

		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
		
		File xmlFile = new File(url+"WEB-INF\\internetbank\\icbc\\config.xml");// 根据指定的路径创建file对象
		
		//File xmlFile = new File("E:\\work\\ework\\CHD-HRP\\WebRoot\\WEB-INF\\internetbank\\icbc\\config.xml");// 根据指定的路径创建file对象
		
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
