package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface GroupAccEmpAuxiliaryAccountMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryGroupAccEmpSubjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccEmpSubjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjEmpGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjEmpDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccEmpEndOs(Map<String,Object> entityMap) throws DataAccessException;

}
