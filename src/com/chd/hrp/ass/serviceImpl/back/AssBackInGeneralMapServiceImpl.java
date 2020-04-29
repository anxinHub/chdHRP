package com.chd.hrp.ass.serviceImpl.back;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.back.AssBackInGeneralMapMapper;
import com.chd.hrp.ass.service.back.AssBackInGeneralMapService;

@Service("assBackInGeneralMapService")
public class AssBackInGeneralMapServiceImpl implements AssBackInGeneralMapService{

	private static Logger logger = Logger.getLogger(AssBackInGeneralMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackInGeneralMapMapper")
	private final AssBackInGeneralMapMapper assBackInGeneralMapMapper = null;
}
