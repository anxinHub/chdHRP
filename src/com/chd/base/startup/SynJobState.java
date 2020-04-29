package com.chd.base.startup;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.chd.base.jdbc.ConfigInit;
import com.chd.hrp.hip.service.HipDataJobService;

public class SynJobState  implements InitializingBean{

    @Autowired
    private ServletContext application;
    @Autowired
	private HipDataJobService dataJobService;
    
    private static Logger logger = Logger.getLogger(SynJobState.class);
    
    @Override
	public void afterPropertiesSet() throws Exception{ 
    	
    	String isJon=ConfigInit.getConfigProperties("loadJob");
    	if (null!=isJon && isJon.equals("false")) {
			return;
		}
    	
		String uri=null;
		try{
			MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
	        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
	                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
	        String host = InetAddress.getLocalHost().getHostAddress();
	        String port = objectNames.iterator().next().getKeyProperty("port");
	        String app=application.getContextPath();
	        uri = "http" + "://" + host + ":" + port+app;
	        dataJobService.synDataJob(uri);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
