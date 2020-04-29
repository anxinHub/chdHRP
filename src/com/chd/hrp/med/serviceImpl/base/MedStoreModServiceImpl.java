package com.chd.hrp.med.serviceImpl.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.base.MedStoreModMapper;
import com.chd.hrp.med.entity.MedStoreMod;
import com.chd.hrp.med.service.base.MedStoreModService;

@Service("medStoreModService")
public class MedStoreModServiceImpl implements MedStoreModService {
	
	private static Logger logger = Logger.getLogger(MedStoreModServiceImpl.class);

	@Resource(name = "medStoreModMapper")
	private final MedStoreModMapper medStoreModMapper = null;
	 
	
	
	@Override
	public String queryStoreMod(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		List<MedStoreMod> list = medStoreModMapper.queryStoreMod(entityMap) ;
		
		return ChdJson.toJson(list);
	}

	@Override
	public String addStoreModStart(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
				
				int state = medStoreModMapper.addStoreModStart(entityMap) ;
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addHosPackage\"}";
	
			}
		
	}
	
	
	
	 
}
