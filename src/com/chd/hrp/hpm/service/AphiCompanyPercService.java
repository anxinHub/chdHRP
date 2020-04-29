package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCompanyPerc;

/**
 * alfred
 */

public interface AphiCompanyPercService {

	/**
	 * 
	 */
	public String addCompanyPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCompanyPerc(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCompanyPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCompanyPerc queryCompanyPercByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCompanyPerc(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCompanyPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createCompanyPerc(Map<String, Object> entityMap) throws DataAccessException;

}
