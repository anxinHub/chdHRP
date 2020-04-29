package com.chd.hrp.ass.serviceImpl.check.other;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.check.other.AssChkDsInOtherMapMapper;
import com.chd.hrp.ass.service.check.other.AssChkDsInOtherMapService;

@Service("assChkDsInOtherMapService")
public class AssChkDsInOtherMapServiceImpl implements AssChkDsInOtherMapService{

	private static Logger logger = Logger.getLogger(AssChkDsInOtherMapServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assChkDsInOtherMapMapper")
	private final AssChkDsInOtherMapMapper assChkDsInOtherMapMapper = null;
}
