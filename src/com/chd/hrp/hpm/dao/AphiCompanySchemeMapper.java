package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiCompanyScheme;

/**
 * alfred
 */
public interface AphiCompanySchemeMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchCompanyScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCompanyScheme> queryCompanyScheme(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCompanyScheme> queryCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCompanyScheme queryCompanySchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;
}
