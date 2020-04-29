package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface GroupAccProjAuxiliaryAccountMapper extends SqlMapper {
	
	public List<Map<String, Object>> queryGroupAccProjSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccProjSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjProjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjProjDetailLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccProjEndOs(Map<String, Object> entityMap) throws DataAccessException;

}
