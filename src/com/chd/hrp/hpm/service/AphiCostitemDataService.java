package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemData;
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

public interface AphiCostitemDataService {

	/**
	 * 
	 */
	public String addCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCostitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemConf> getCostItemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemData queryCostitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemDataCodes(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemConf getCostItemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getCostitemTargetValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入数据跳转 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String hpmcostitemDataImport(Map<String, Object> entityMap) throws DataAccessException;


}
