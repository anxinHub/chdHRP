package com.chd.hrp.ass.serviceImpl.check.general;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.check.general.AssChkDsInGeneralMapMapper;
import com.chd.hrp.ass.service.check.general.AssChkDsInGeneralMapService;

@Service("assChkDsInGeneralMapService")
public class AssChkDsInGeneralMapServiceImpl implements AssChkDsInGeneralMapService {

private static Logger logger = Logger.getLogger(AssChkDsInGeneralMapServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assChkDsInGeneralMapMapper")
	private final AssChkDsInGeneralMapMapper assChkDsInGeneralMapMapper = null;
}
