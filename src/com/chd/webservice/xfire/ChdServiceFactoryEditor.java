package com.chd.webservice.xfire;

import org.codehaus.xfire.spring.editors.ServiceFactoryEditor;
import org.codehaus.xfire.transport.TransportManager;
import org.springframework.web.context.ContextLoaderListener;

public class ChdServiceFactoryEditor extends ServiceFactoryEditor {

	@Override
	public TransportManager getTransportManager() {
		if (null == super.getTransportManager()) {
			super.setTransportManager(ContextLoaderListener.getCurrentWebApplicationContext().getBean("xfire.transportManager", TransportManager.class));
		}
		// TODO Auto-generated method stub
		return super.getTransportManager();
	}

}
