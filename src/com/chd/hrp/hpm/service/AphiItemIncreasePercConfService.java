package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;

/**
 * alfred
 */

public interface AphiItemIncreasePercConfService {

	/**
	 * 
	 */
	public String addItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchItemIncreasePercConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreasePercConf queryItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteBatchItemIncreasePercConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;
}
