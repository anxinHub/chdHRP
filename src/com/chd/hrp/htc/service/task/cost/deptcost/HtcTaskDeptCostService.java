package com.chd.hrp.htc.service.task.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.cost.deptcost.HtcTaskDeptCost;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcTaskDeptCostService {

	/**
	 * 
	 */
	public String addHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String addBatchHtcTaskDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcTaskDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcTaskDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcTaskDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcTaskDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcTaskDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public HtcTaskDeptCost queryHtcTaskDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/** 
	 * 
	 */
	public String deleteBatchHtcTaskDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 导入
	 */
	public String impHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	

}
