package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptTarget;

/**
 * alfred
 */

public interface AphiDeptTargetService {

	/**
	 * 
	 */
	public String addDeptTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptTarget(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptTarget queryDeptTargetByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptTarget(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptTarget(Map<String, Object> entityMap) throws DataAccessException;
}
