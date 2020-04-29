package com.chd.hrp.ass.serviceImpl.back;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.back.AssBackInInassetsMapMapper;
import com.chd.hrp.ass.service.back.AssBackInInassetsMapService;

@Service("assBackInInassetsMapService")
public class AssBackInInassetsMapServiceImpl implements AssBackInInassetsMapService{

	private static Logger logger = Logger.getLogger(AssBackInInassetsMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackInInassetsMapMapper")
	private final AssBackInInassetsMapMapper assBackInInassetsMapMapper = null;


}
