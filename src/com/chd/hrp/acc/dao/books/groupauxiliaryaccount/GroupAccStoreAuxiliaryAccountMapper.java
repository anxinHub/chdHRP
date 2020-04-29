package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface GroupAccStoreAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryGroupAccStoreSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccStoreSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjStoreGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjStoreDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccStoreEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
}
