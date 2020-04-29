package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItem;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiDeptBonusDataService {

	/**
	 * 
	 */
	public String initHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHpmDeptBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBonusData queryHpmDeptBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String collectHpmDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHpmDeptBonusDataForTarget(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHpmDeptBonusDataForFormula(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	//public String insertDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	//public String queryDeptBonusAuitData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询奖金核算的表头
	 */
	public String queryHpmDeptBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询奖金查询的表头
	 */
	public String queryHpmDeptBonusDataForTargetGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询奖金查询的表头 返回list
	 */
	public List<AphiItem> getGridTitleMap(Map<String, Object> entityMap);

}
