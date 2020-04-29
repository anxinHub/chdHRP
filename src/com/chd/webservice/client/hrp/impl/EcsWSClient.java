package com.chd.webservice.client.hrp.impl;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.log4j.Logger;

public class EcsWSClient {

	private static Logger logger = Logger.getLogger(EcsWSClient.class);
	
//	public static final String SERVER_WSDL_URL = "http://192.144.144.119:8080/HRP_ECS/webservice/Service?wsdl";
//	public static final String METHOD_NAME = "ecsService";
	public static final String SERVER_WSDL_URL = "http://58.56.200.231:9090/HRP-SIP/webservice/hrpService?wsdl";
	public static final String METHOD_NAME = "hrpServcie";
//	public static final String SERVER_WSDL_URL = "http://localhost:8080/HRP_QZ/webservice/hrpService?wsdl";
//	public static final String NAME_SPACE = "http://service.hrp.server.webservice.com/";
//	public static final String METHOD_NAME = "hrpServcie";
	
	public String invoke (String opcode, String data) {
		// url为调用webService的wsdl地址
        org.apache.cxf.endpoint.Client client = JaxWsDynamicClientFactory.newInstance().createClient(SERVER_WSDL_URL);
        
        QName name = getOperateQName(client, METHOD_NAME);
        // 返回值
        String response = "";
        try {
            Object[] objects = client.invoke(name, opcode, data);
            response = objects[0].toString();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	response = "{\"state\": false, \"msg\": \"webservice客户端调用异常\"}";
        }
		return response;
	}
	
	public static void main(String[] args) {
		
		org.apache.cxf.endpoint.Client client = JaxWsDynamicClientFactory.newInstance().createClient(SERVER_WSDL_URL);
        
        // namespace是命名空间，methodName是方法名
        QName name = getOperateQName(client, METHOD_NAME);
        // 返回值
        String response = "";
        try {
        	String data = "{\"group_id\": 100,\"hos_id\": 102,\"copy_code\": \"001\"}";
        	
            Object[] objects = client.invoke(name, "123", "321", "updateHosInv", data);
//            Object[] objects = client.invoke(name, "queryHosInvDict", data);
            response = objects[0].toString();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		System.out.println(response);
		
		
	}
	
	/**
	 * 针对SEI和SIB不在统一个包内的情况，先查找操作对应的Qname，
	 * client通过Qname调用对应操作
	 * @param client
	 * @param operation
	 * @return
	 */
	private static QName getOperateQName(Client client, String operation){
		Endpoint endpoint = client.getEndpoint();
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), operation);
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
		if (bindingInfo.getOperation(opName) == null) {
			for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
				if (operation.equals(operationInfo.getName().getLocalPart())) {
					opName = operationInfo.getName();
					break;
				}
			}
		}
		logger.debug(("Operation:"+operation+",namespaceURI:" + opName.getNamespaceURI()));
		return opName;
	}
	
}
