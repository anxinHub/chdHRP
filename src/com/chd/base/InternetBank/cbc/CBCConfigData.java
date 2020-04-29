package com.chd.base.InternetBank.cbc;

import com.chd.base.jdbc.ConfigInit;

public class CBCConfigData {
	
	String etlPath=ConfigInit.getConfigProperties("etlPath");
	String jobPath=ConfigInit.getConfigProperties("jobPath");
}
