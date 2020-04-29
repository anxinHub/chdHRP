package com.chd.hrp.htc.dao.income.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcIncomeDeptCostMapper extends SqlMapper{

	/**
	 * 
	 */
	public int addHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcIncomeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<HtcIncomeDeptCost> queryHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	public List<HtcIncomeDeptCost> queryHtcIncomeDeptCost(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcIncomeDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostTotAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostPrimeAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostPubAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostManAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcIncomeDeptCostAssAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public HtcIncomeDeptCost queryHtcIncomeDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteHtcIncomeDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteBatchHtcIncomeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

}
