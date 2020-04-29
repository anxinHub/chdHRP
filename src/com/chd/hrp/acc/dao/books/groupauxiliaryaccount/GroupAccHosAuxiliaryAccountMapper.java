package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccHosAuxiliaryAccount;


public interface GroupAccHosAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryGroupAccHosSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccHosSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjHosGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjHosDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccHosAuxiliaryAccount> queryGroupAccHosEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
}
