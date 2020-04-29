package com.chd.hrp.htc.dao.relative.cost.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcRelativeDeptCostMapper extends SqlMapper{

	/**
	 * 
	 */
	public int addHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcRelativeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<HtcRelativeDeptCost> queryHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;
	public List<HtcRelativeDeptCost> queryHtcRelativeDeptCost(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcRelativeDeptCostTotAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostTotAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostPrimeAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostPrimeAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostPubAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostPubAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostManAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostManAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostAssAmount(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHtcRelativeDeptCostAssAmount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public HtcRelativeDeptCost queryHtcRelativeDeptCostByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteHtcRelativeDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteBatchHtcRelativeDeptCost(List<Map<String, Object>> entityMap) throws DataAccessException;

}
