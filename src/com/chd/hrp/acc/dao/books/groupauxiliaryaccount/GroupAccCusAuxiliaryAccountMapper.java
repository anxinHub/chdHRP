package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface GroupAccCusAuxiliaryAccountMapper  extends SqlMapper{
	public List<Map<String, Object>> queryGroupAccCusSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccCusSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjCusGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjCusDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccCusEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
		
}
