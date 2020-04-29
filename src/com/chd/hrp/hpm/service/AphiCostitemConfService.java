package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCostitemConf;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiCostitemConfService {

	/**
	 * 
	 */
	public String addCostitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchCostitemConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCostitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemConf queryCostitemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCostitemConf(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCostitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createCostitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String copyCostitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String hpmCostitemConfImport(Map<String, Object> entityMap)throws DataAccessException;
}
