/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;

/**
* @Title. @Description.
* 成本_职工分类工资项配置表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpKindWageItemSetService {

	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 添加CostEmpKindWageItemSet
	 * @param CostEmpKindWageItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量添加CostEmpKindWageItemSet
	 * @param  CostEmpKindWageItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpKindWageItemSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpKindWageItemSet queryCostEmpKindWageItemSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryCostEmpWageList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpWageMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 通过职工分类查询工资项编码  queryCostEmpKindWageItemSetByEmpKindCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostEmpKindWageItemSet> queryCostEmpKindWageItemSetByEmpKindCode(Map<String,Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpKindWageItemSetPrint(Map<String,Object> entityMap) throws DataAccessException;
}
