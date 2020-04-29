package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccStoreAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryStoreSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccStoreSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjStoreGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjStoreDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryStoreEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
}
