package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptKindScheme;

/**
 * alfred
 */

public interface AphiDeptKindSchemeService {

	/**
	 * 
	 */
	public String addDeptKindScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addDeptKindScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptKindScheme queryDeptKindSchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptKindScheme(Map<String, Object> mapVo, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptKindScheme(Map<String, Object> entityMap) throws DataAccessException;
}
