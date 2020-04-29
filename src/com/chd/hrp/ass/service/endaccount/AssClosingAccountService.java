package com.chd.hrp.ass.service.endaccount;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssClosingAccountService {
	public String queryAssYearMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> queryAssCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectDepreALL(Map<String,Object> map)throws DataAccessException;
	
	public String collectDelDepreALL(Map<String,Object> map)throws DataAccessException;

	public Map<String, Object> queryAssSysYearMonth(Map<String, Object> mapVo)throws DataAccessException;
	
}
