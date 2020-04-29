/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBonusSchemeSet;

/**
* @Title. @Description.
* 职工奖金查询方案表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusSchemeSetService {

	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 添加CostBonusSchemeSet
	 * @param CostBonusSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量添加CostBonusSchemeSet
	 * @param  CostBonusSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostBonusSchemeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostBonusSchemeSetByTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostBonusSchemeSet> queryCostBonusSchemeSetByTitleList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostBonusSchemeSet queryCostBonusSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryCostBonusList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostBonusMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostBonusItemList(Map<String,Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostBonusSchemeSetPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
