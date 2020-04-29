package com.chd.hrp.hr.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HrStoreModService {
	public String queryMod(Map<String,Object> entityMap) throws DataAccessException;
	public String addModStart(Map<String,Object> entityMap) throws DataAccessException;
}
