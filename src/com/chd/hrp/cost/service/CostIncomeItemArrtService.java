/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostIncomeItemArrt;

/**
* @Title. @Description.
* 成本_收入项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeItemArrtService {

	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 添加CostIncomeItemArrt
	 * @param CostIncomeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量添加CostIncomeItemArrt
	 * @param  CostIncomeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIncomeItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrt
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostIncomeItemArrt> queryCostIncomeItemArrtList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIncomeItemArrt queryCostIncomeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIncomeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostIncomeItemArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
