/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostIassetArrt;

/**
* @Title. @Description.
* 成本_无形资产字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 添加CostIassetArrt
	 * @param CostIassetArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量添加CostIassetArrt
	 * @param  CostIassetArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostIassetArrt>
	 * @throws DataAccessException
	*/
	public List<CostIassetArrt> queryCostIassetArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrt所有数据
	 * @param  entityMap
	 * @return List<CostIassetArrt>
	 * @throws DataAccessException
	*/
	public List<CostIassetArrt> queryCostIassetArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIassetArrt queryCostIassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 删除CostIassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量删除CostIassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 更新CostIassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量更新CostIassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostIassetArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
