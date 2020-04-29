package com.chd.hrp.ass.serviceImpl.check.special;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.check.special.AssChkDsInMapMapper;
import com.chd.hrp.ass.service.check.special.AssChkDsInMapService;

@Service("assChkDsInMapService")
public class AssChkDsInMapServiceImpl implements AssChkDsInMapService {

	private static Logger logger = Logger.getLogger(AssChkDsInMapServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assChkDsInMapMapper")
	private final AssChkDsInMapMapper assChkDsInMapMapper = null;
	
}
