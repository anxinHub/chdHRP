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
import com.chd.hrp.cost.entity.CostChargeKindArrt;

/**
* @Title. @Description.
* 成本_收费类别字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostChargeKindArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 添加CostChargeKindArrt
	 * @param CostChargeKindArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量添加CostChargeKindArrt
	 * @param  CostChargeKindArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostChargeKindArrt>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCostChargeKindArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt所有数据
	 * @param  entityMap
	 * @return List<CostChargeKindArrt>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCostChargeKindArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostChargeKindArrt queryCostChargeKindArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	
}
