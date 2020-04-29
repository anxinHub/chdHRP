package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccDeptAuxiliaryAccount;

public interface AccDeptAuxiliaryAccountMapper  extends SqlMapper{
	
	public List<Map<String, Object>> queryDeptSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccDeptSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjDeptGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjDeptDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccDeptBalance(Map<String,Object> entityMap) throws DataAccessException;

}
