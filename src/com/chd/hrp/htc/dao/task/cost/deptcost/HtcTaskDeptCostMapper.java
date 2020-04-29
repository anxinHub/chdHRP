package com.chd.hrp.htc.dao.task.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcTaskDeptCostMapper extends SqlMapper{

	/**
	 * 
	 */
	public int addHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcTaskDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<HtcTaskDeptCost> queryHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	public List<HtcTaskDeptCost> queryHtcTaskDeptCost(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcTaskDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostTotAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostPrimeAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostPubAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostManAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcTaskDeptCostAssAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public HtcTaskDeptCost queryHtcTaskDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteHtcTaskDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteBatchHtcTaskDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

}
