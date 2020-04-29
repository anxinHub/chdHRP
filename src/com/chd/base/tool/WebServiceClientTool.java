package com.chd.base.tool;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.log4j.Logger;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.Mvcs;
 
/**
 * 
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2018年9月13日 上午8:38:31
 * @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class WebServiceClientTool {
 
	private static Logger logger = Logger.getLogger(WebServiceClientTool.class);

	/**
	 * 动态构建服务端服务类
	 * 
	 * @return
	 */
	public static JaxWsDynamicClientFactory getClientFactory() {

		long beginTime = System.currentTimeMillis(); // 开始时间
		// 动态构建服务端服务类
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();

		long endTime = System.currentTimeMillis(); // 开始时间

		long time = endTime - beginTime;

		logger.debug("构建服务端服务类耗时：" + time / 1000 + " s");

		return clientFactory;
	}

	/**
	 * 构建客户端
	 * 
	 * @param url
	 *            服务端请求的地址
	 * @return
	 */
	public static Client getClient(String url) {

		long beginTime = System.currentTimeMillis(); // 开始时间

		Client client = getClientFactory().createClient(url);

		long endTime = System.currentTimeMillis(); // 开始时间

		long time = endTime - beginTime;

		logger.debug("构建客户端Client耗时：" + time / 1000 + " s");

		return client;
	}

	/**
	 * 服务端响应结果
	 * 
	 * @param client
	 *            客户端实体类
	 * @param method
	 *            服务端请求方法
	 * @param objs
	 *            服务端请求参数
	 * @return
	 */
	public static Object[] responseResult(Client client, String method, Object... objs) {

		logger.debug("请求的方法是：" + method);
		logger.debug("请求的参数是：" + Json.toJson(objs, JsonFormat.full()));

		long beginTime = System.currentTimeMillis(); // 开始时间

		Object[] result = null;

		try {

			result = client.invoke(method, objs);

		} catch (Exception e) {

			e.printStackTrace();

		}

		long endTime = System.currentTimeMillis(); // 开始时间

		long time = endTime - beginTime;

		logger.debug("调用方法[" + method + "]耗时：" + time / 1000 + " s");

		return result;

	}

	/**
	 * 服务端响应结果
	 * 
	 * @param url
	 *            服务端请求URL
	 * @param method
	 *            服务端请求方法
	 * @param objs
	 *            服务端请求参数
	 * @return
	 */
	public static Object[] responseResult(String url, String method, Object... objs) {

		logger.debug("请求的方法是：" + method);
		logger.debug("请求的参数是：" + Json.toJson(objs, JsonFormat.full()));

		long beginTime = System.currentTimeMillis(); // 开始时间

		Object[] result = null;

		try {

			result = getClient(url).invoke(method, objs);

		} catch (Exception e) {

			e.printStackTrace();

		}

		long endTime = System.currentTimeMillis(); // 开始时间

		long time = endTime - beginTime;

		logger.debug("调用方法[" + method + "]耗时：" + time / 1000 + " s");

		return result;

	}
	/**
	 * 获取 system.properties 文件中的配置信息</br>
	 * 调用方法 .get(key);
	 * 
	 * @return
	 */
	public static PropertiesProxy getProperties() {
		PropertiesProxy conf = Mvcs.ctx().getDefaultIoc().get(PropertiesProxy.class, "conf");

		// 避免数据库 用户名 密码泄露 这里清除与数据库相关的配置信息
		// ---------------------------------------------
		conf.remove("url");
		conf.remove("username");
		conf.remove("password");
		conf.remove("driverClassName");
		// ---------------------------------------------
		return conf;
	}
}
