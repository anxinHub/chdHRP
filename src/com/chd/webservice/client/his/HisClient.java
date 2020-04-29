package com.chd.webservice.client.his;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.chd.webservice.server.his.HisService;

/**
 * @Copyright: Copyright (c) 2017年2月27日 下午1:44:32
 * @Company: 杭州亦童科技有限公司
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 6.0
 */
public class HisClient {
	private static final Log logger = Logs.getLog(HisClient.class);
	public static void main(String[] args) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(HisService.class);
		factory.setAddress("http://localhost:9090/CHD-HRP/webservice/hisService?wsdl");
		//factory.setAddress("http://localhost:8090/CHD-HRP/service/HisService?wsdl");
		
		HisService service = (HisService) factory.create();
		logger.debug("#############Client 获取服务端反馈的核销信息##############");
		//返回JSON格式
		String mags = service.hrpCommIof("1000", "100021", 1, 3, "01","11", "88|收费类别44|01|^", 0);
		logger.debug(mags);
		System.out.println(mags);
		//查询结果集
		String mags1 = service.hrpCommIof("1004", "100410", 1, 3, "01", "11","1", 0);
		logger.debug(mags1);
		
		System.out.println(mags1);
		
		//返回集合格式
		//Map<String, Object> map = service.hrpCommIofToMap("1000", "100021","11", 6, 3, "01", "88|收费类别44|01|^", 0);
		//logger.debug(Json.toJson(map));
		//System.out.println(map);
		//查询结果集
		//Map<String, Object> map1 = service.hrpCommIofToMap("1004", "100410","11", 1, 3, "01", "1", 0);
		
		//logger.debug(map1);
		//System.out.println(map1);
		logger.debug("#############Client 获取服务端反馈的核销信息##############");
	}
}
