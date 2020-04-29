package com.chd.base.tool;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

public class PropertiesTool {
	
	PropertiesTool() {

	}

	private static final Log logger = Logs.get();

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
