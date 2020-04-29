package com.chd.hrp.htcg.service.syset;

import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HtcgStartModService {
	
	public String queryhtcgMod(Map<String,Object> entityMap) throws DataAccessException;
	
	public String saveHtcgMod(Map<String,Object> entityMap) throws DataAccessException;
}
