package com.chd.base.tool;

import java.util.Properties;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

public class ServerUrl {
	private Properties conf;
	private Properties serverUrl;

	 
	 public ServerUrl() {
		try {
			getConfInit();
			getServerUrlInit();
		} catch (Exception e) {
			e.printStackTrace();
 
		}
	}

	/**
	 * 获取HttpClient配置信息
	 * 
	 * @throws Exception
	 */
	private void getConfInit() throws Exception {
		conf = new Properties();
		conf.load(getClass().getClassLoader().getResourceAsStream("ioc/httpclient.properties"));

	}

	/**
	 * 获取省平台发布的接口URL
	 * 
	 * @throws Exception
	 */
	private void getServerUrlInit() throws Exception {
		serverUrl = new Properties();
		serverUrl.load(getClass().getClassLoader().getResourceAsStream("ioc/serverUrl.properties"));
	}

	
	/**
	 * 获取HttpClient配置信息
	 * 
	 * @throws Exception
	 */
	public  Properties getConf() throws Exception {

		if (Lang.isEmpty(conf))
			conf = new Properties();

		return conf;
	}

	/**
	 * 获取省平台发布的接口URL集合
	 * 
	 * @throws Exception
	 */
	public  Properties getServerUrl() throws Exception {

		if (Lang.isEmpty(serverUrl))
			serverUrl = new Properties();

		return serverUrl;
	}


	/**
	 * 获取省平台发布的接口URL
	 * 
	 * @param ip
	 *            供应商发布的IP地址 由HRP标识进行转换 示例：供应商平台IP -->chdsup.ip<br>
	 * @param key
	 *            各省份发布的业务接口地址 由HRP标识进行转换 示例：供应商平台业务接口地址 -->chdsup.H001 <br>
	 *            描述： 发送订单请求 供应商平台IP -->chdsup.ip<br>
	 * @return 最终请求的URL
	 * @throws Exception
	 */
	public  String getProperty(String ip, String key) throws Exception {

		if (Lang.isEmpty(serverUrl)) {
			serverUrl = new Properties();
			return null;
		}
		String _ip = serverUrl.getProperty(ip);
		if (Strings.isEmpty(_ip))
			return null;
		String _busi = serverUrl.getProperty(key);
		if (Strings.isEmpty(_busi))
			return null;
		return _ip + _busi;
	}

}
