package com.chd.hrp.cost.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostStartModService {

	public String queryMod(Map<String,Object> entityMap) throws DataAccessException;
	public String addModStart(Map<String,Object> entityMap) throws DataAccessException;

}
