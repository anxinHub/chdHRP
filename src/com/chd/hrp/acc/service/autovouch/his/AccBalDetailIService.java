package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccBalDetailIService {
	public String queryAccBalDetailI(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccBalDetailIPrint(Map<String, Object> entityMap) throws DataAccessException;
}
