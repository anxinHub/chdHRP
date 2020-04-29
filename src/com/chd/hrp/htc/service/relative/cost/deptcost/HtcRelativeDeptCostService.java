package com.chd.hrp.htc.service.relative.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.relative.cost.deptcost.HtcRelativeDeptCost;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcRelativeDeptCostService {

	/**
	 * 
	 */
	public String addHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String addBatchHtcRelativeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcRelativeDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcRelativeDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcRelativeDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcRelativeDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHtcRelativeDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public HtcRelativeDeptCost queryHtcRelativeDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/** 
	 * 
	 */
	public String deleteBatchHtcRelativeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 导入
	 */
	public String impHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	

}
