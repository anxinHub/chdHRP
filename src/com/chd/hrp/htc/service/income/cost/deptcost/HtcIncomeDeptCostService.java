package com.chd.hrp.htc.service.income.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.income.cost.deptcost.HtcIncomeDeptCost;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcIncomeDeptCostService {

	/**
	 * 
	 */
	public String addHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String addBatchHtcIncomeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcIncomeDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcIncomeDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcIncomeDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcIncomeDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcIncomeDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public HtcIncomeDeptCost queryHtcIncomeDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/** 
	 * 
	 */
	public String deleteBatchHtcIncomeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 导入
	 */
	public String impHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	

}
