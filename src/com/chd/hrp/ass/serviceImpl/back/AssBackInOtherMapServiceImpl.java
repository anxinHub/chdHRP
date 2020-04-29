package com.chd.hrp.ass.serviceImpl.back;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.back.AssBackInOtherMapMapper;
import com.chd.hrp.ass.service.back.AssBackInOtherMapService;

@Service("assBackInOtherMapService")
public class AssBackInOtherMapServiceImpl implements AssBackInOtherMapService{

	private static Logger logger = Logger.getLogger(AssBackInSpecialMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackInOtherMapMapper")
	private final AssBackInOtherMapMapper assBackInOtherMapMapper = null;
}
