package com.chd.hrp.ass.service.assmap;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssAssMapService {
	public String queryAssStoreDistribution(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAssDeptDistribution(Map<String,Object> entityMap)throws DataAccessException;

	public String queryDeptAssDistribution(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAssCirculationView(Map<String,Object> entityMap)throws DataAccessException;
}
