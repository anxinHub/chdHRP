package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptLimitConf;

/**
 * alfred
 */

public interface AphiDeptLimitConfService {

	/**
	 * 
	 */
	public String addDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptLimitConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptLimitConf queryDeptLimitConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptLimitConf(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String hpmDeptLimitConfImport(Map<String, Object> entityMap) throws DataAccessException;
}
