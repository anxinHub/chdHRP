package com.chd.hrp.med.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface MedStoreModService {
	//查询库房是否启用
	public String queryStoreMod(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addStoreModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	 
}
