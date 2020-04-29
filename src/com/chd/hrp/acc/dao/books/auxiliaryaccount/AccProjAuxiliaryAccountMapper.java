package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AccProjAuxiliaryAccountMapper extends SqlMapper {
	
	public List<Map<String, Object>> queryProjSubjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccProjSubjDetailLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjProjGeneralLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjProjDetailLedger(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryProjEndOs(Map<String, Object> entityMap) throws DataAccessException;

	/** 查询tablename 查询动态列信息 */
	public List<Map<String, Object>> queryProjByCode(Map<String, Object> entityMap) throws DataAccessException;

}
