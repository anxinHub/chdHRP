package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface GroupAccSupAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryGroupAccSupSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSupSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjSupGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjSupDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSupEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
}
