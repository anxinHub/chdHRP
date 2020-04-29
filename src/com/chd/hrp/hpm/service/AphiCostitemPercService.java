package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCostitemPerc;

/**
 * alfred
 */

public interface AphiCostitemPercService {

	/**
	 * 
	 */
	public String addCostitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCostitemPerc(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCostitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemPerc queryCostitemPercByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemPerc(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCostitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createCostitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastCostitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String hpmCostitemConfImport(Map<String, Object> entityMap) throws DataAccessException;

}
