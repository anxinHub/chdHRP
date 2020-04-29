package com.chd.hrp.hpm.serviceImpl;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.hpm.dao.AphiAppModMapper;
import com.chd.hrp.hpm.entity.AphiAppMod;
import com.chd.hrp.hpm.service.AphiAppModService;
@Service("aphiAppModService")
public class AphiAppModServiceImpl implements AphiAppModService{
   
	private static Logger logger = Logger.getLogger(AphiAppModServiceImpl.class);
	
	@Resource(name = "aphiAppModMapper")
	private AphiAppModMapper aphiAppModMapper = null;
	
	@Override
	public AphiAppMod queryAppModByCode(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return aphiAppModMapper.queryAppModByCode(mapVo);
	
	}

}
