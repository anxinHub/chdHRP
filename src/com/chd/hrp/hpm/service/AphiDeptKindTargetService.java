package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptKindTarget;

/**
 * alfred
 */

public interface AphiDeptKindTargetService {

	/**
	 * 
	 */
	public String addDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptKindTarget(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptKindTarget queryDeptKindTargetByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptKindTarget(Map<String, Object> mapVo, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException;
}
