package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;

/**
 * alfred
 */

public interface AphiIncomeitemPercService {

	/**
	 * 
	 */
	public String addIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitemPerc(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPerc queryIncomeitemPercByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemPerc(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastIncomeitemPerc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String hpmIncomeitemPercImport(Map<String, Object> entityMap)throws DataAccessException;
}
