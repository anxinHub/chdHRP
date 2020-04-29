package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItemIncreaseData;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
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

public interface AphiItemIncreaseDataService {

	/**
	 * 
	 */
	public String addItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchItemIncreaseData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreasePercConf> getItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreaseData queryItemIncreaseDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */

	public String deleteItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteItemIncreaseData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreaseData> queryItemIncreaseDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusData> queryDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreasePercConf getItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getItemIncreaseDataValue(Map<String, Object> entityMap) throws DataAccessException;
}
