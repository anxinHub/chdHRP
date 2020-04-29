package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCompanyScheme;

/**
 * alfred
 */

public interface AphiCompanySchemeService {

	/**
	 * 
	 */
	public String addCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCompanyScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCompanyScheme queryCompanySchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCompanyScheme(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCompanyScheme(Map<String, Object> entityMap) throws DataAccessException;
}
