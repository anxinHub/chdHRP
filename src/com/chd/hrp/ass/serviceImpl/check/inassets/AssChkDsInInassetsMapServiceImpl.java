package com.chd.hrp.ass.serviceImpl.check.inassets;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.check.inassets.AssChkDsInInassetsMapMapper;
import com.chd.hrp.ass.service.check.inassets.AssChkDsInInassetsMapService;

@Service("assChkDsInInassetsMapService")
public class AssChkDsInInassetsMapServiceImpl implements AssChkDsInInassetsMapService{

	private static Logger logger = Logger.getLogger(AssChkDsInInassetsMapServiceImpl.class);
	
	//引入DAO操作
		@Resource(name = "assChkDsInInassetsMapMapper")
		private final AssChkDsInInassetsMapMapper assChkDsInInassetsMapMapper = null;
}
