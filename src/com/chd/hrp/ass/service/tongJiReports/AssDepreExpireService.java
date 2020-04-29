package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssDepreExpireService {

	String queryAssDepreExpire(Map<String, Object> entityMap)throws DataAccessException;

	String queryAssDepreTerm(Map<String, Object> entityMap)throws DataAccessException;
	/**
	  * 资产折旧到期打印
	  */
	public List<Map<String, Object>> queryAssDepreExpirePrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 资产折旧年限打印
	  */
	public List<Map<String, Object>> queryAssDepreTermPrint(Map<String, Object> entityMap) throws DataAccessException;
}
