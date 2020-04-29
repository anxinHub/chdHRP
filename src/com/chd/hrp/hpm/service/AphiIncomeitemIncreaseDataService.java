package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeitemIncreaseData;
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;
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

public interface AphiIncomeitemIncreaseDataService {

	/**
	 * 
	 */
	public String addIncomeitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitemIncreaseData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initIncomeitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPerc> getIncomeItemPercSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryIncomeitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemIncreaseData queryIncomeitemIncreaseDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemIncreaseData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateIncomeitemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPerc getIncomeItemPercSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getIncomeitemIncreaseValue(Map<String, Object> entityMap) throws DataAccessException;
}
