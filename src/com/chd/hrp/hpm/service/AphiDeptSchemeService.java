package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptScheme;

/**
 * alfred
 */

public interface AphiDeptSchemeService {

	/**
	 * 
	 */
	public String addDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptScheme queryDeptSchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptScheme(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastDeptScheme(Map<String, Object> entityMap) throws DataAccessException;
}
