package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccCusAuxiliaryAccountMapper  extends SqlMapper{
	public List<Map<String, Object>> queryCusSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccCusSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjCusGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjCusDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCusEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
		
}
