package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCostitemIncreaseData;
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.entity.AphiSchemeConf;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiCostitemIncreaseDataService {

	/**
	 * 
	 */
	public String addCostitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCostitemIncreaseData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initCostitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemPerc> getCostItemPercSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCostitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemIncreaseData queryCostitemIncreaseDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemIncreaseData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCostitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemPerc getCostItemPercSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getCostitemIncreaseValue(Map<String, Object> entityMap) throws DataAccessException;
}
