package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;

/**
 * alfred
 */

public interface AphiIncomeitemPointService {

	/**
	 * 
	 */
	public String addIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitemPoint(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPoint queryIncomeitemPointByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemPoint(Map<String, Object> mapVo, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastIncomeitemPoint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addIncomeitemPointValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String hpmIncomeitemPointImport(Map<String, Object> entityMap) throws DataAccessException;

}
