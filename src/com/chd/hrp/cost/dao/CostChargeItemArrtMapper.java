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
import com.chd.hrp.cost.entity.CostChargeItemArrt;

/**
* @Title. @Description.
* 成本_收费项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostChargeItemArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 添加CostChargeItemArrt
	 * @param CostChargeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量添加CostChargeItemArrt
	 * @param  CostChargeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostChargeItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostChargeItemArrt> queryCostChargeItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostChargeItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostChargeItemArrt> queryCostChargeItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostChargeItemArrt>
	 * @throws DataAccessException
	 */
	public List<CostChargeItemArrt> queryCostChargeItemArrtByKindCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostChargeItemArrt queryCostChargeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostChargeItemArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
