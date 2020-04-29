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
import com.chd.hrp.cost.entity.CostIncomeItemArrt;

/**
* @Title. @Description.
* 成本_收入项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeItemArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 添加CostIncomeItemArrt
	 * @param CostIncomeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量添加CostIncomeItemArrt
	 * @param  CostIncomeItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostIncomeItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostIncomeItemArrt> queryCostIncomeItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostIncomeItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostIncomeItemArrt> queryCostIncomeItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIncomeItemArrt queryCostIncomeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostIncomeItemArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
