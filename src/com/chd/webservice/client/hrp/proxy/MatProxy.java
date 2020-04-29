package com.chd.webservice.client.hrp.proxy;

import javax.jws.WebService;

@WebService(targetNamespace = "http://service.hrp.server.webservice.com/")
public interface MatProxy{

	public String pushSupProSpecAudit(String data);
	
	public String pushMathOrder(String data);

	public String pushSupPsdState(String data);
}
