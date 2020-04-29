package com.chd.hrp.mat.serviceImpl.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.base.MatStoreModMapper;
import com.chd.hrp.mat.entity.MatStoreMod;
import com.chd.hrp.mat.service.base.MatStoreModService;

@Service("matStoreModService")
public class MatStoreModServiceImpl implements MatStoreModService {
	
	private static Logger logger = Logger.getLogger(MatStoreModServiceImpl.class);

	@Resource(name = "matStoreModMapper")
	private final MatStoreModMapper matStoreModMapper = null;
	 
	
	
	@Override
	public String queryStoreMod(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		List<MatStoreMod> list = matStoreModMapper.queryStoreMod(entityMap) ;
		
		return ChdJson.toJson(list);
	}

	@Override
	public String addStoreModStart(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
				
				int state = matStoreModMapper.addStoreModStart(entityMap) ;
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addHosPackage\"}";
	
			}
		
	}
	
	
	
	 
}
