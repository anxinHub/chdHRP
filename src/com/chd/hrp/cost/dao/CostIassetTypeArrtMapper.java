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
import com.chd.hrp.cost.entity.CostIassetTypeArrt;

/**
* @Title. @Description.
* 成本_无形资产分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetTypeArrtMapper extends SqlMapper{
	 
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 添加CostIassetTypeArrt
	 * @param CostIassetTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量添加CostIassetTypeArrt
	 * @param  CostIassetTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostIassetTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostIassetTypeArrt> queryCostIassetTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrt所有数据
	 * @param  entityMap
	 * @return List<CostIassetTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostIassetTypeArrt> queryCostIassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIassetTypeArrt queryCostIassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostIassetTypeArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	
	public void syncCostIassetTypeArrt(Map<String, Object> entityMap);
	
}
