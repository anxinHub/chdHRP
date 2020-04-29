/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;

/**
* @Title. @Description.
* 成本_职工分类奖金项配置表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpKindBonusItemSetService {

	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 添加CostEmpKindBonusItemSet
	 * @param CostEmpKindBonusItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量添加CostEmpKindBonusItemSet
	 * @param  CostEmpKindBonusItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpKindBonusItemSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpKindBonusItemSet queryCostEmpKindBonusItemSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryCostEmpBonusList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpBonusMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpBonusItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostEmpBonusItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 通过职工分类查询奖金项目
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostEmpKindBonusItemSet> queryCostBonusCostRelaByEmpKindCode(Map<String,Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpKindBonusItemSetPrint(Map<String,Object> entityMap) throws DataAccessException;
}
