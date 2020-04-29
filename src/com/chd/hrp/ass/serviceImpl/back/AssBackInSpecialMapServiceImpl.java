package com.chd.hrp.ass.serviceImpl.back;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.back.AssBackInSpecialMapMapper;
import com.chd.hrp.ass.service.back.AssBackInSpecialMapService;

@Service("assBackInSpecialMapService")
public class AssBackInSpecialMapServiceImpl implements AssBackInSpecialMapService{

	private static Logger logger = Logger.getLogger(AssBackInSpecialMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackInSpecialMapMapper")
	private final AssBackInSpecialMapMapper assBackInSpecialMapMapper = null;
}
