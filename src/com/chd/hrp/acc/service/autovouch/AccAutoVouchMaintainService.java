package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccAutoVouchMaintainService {
	public String queryAccAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteAccAutoVouch(List<Map<String, Object>> entityMap) throws DataAccessException;
}
