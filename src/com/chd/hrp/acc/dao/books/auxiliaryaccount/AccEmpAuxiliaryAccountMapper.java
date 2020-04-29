package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccEmpAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryEmpSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccEmpSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjEmpGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjEmpDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryEmpEndOs(Map<String,Object> entityMap) throws DataAccessException;

}
