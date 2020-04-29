package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccHosAuxiliaryAccount;


public interface AccHosAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryHosSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccHosSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjHosGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjHosDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccHosAuxiliaryAccount> queryHosEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
}
