package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemData;
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

public interface AphiIncomeitemDataService {

	/**
	 * 
	 */
	public String addIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemConf> getIncomeItemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemDataCodes(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemData queryIncomeitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateIncomeitemData(Map<String, Object> entityMap) throws DataAccessException;

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
	public AphiIncomeitemConf getIncomeItemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getIncomeitemTargetValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入跳转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String incomeitemDataImport(Map<String, Object> entityMap) throws DataAccessException;

	public String addHisHrp(Map<String, Object> mapVo) throws DataAccessException;
}
