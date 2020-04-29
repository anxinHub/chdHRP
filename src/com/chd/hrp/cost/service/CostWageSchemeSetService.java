/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostWageSchemeSet;

/**
* @Title. @Description.
* 职工工资查询方案表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageSchemeSetService {

	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 添加CostWageSchemeSet
	 * @param CostWageSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量添加CostWageSchemeSet
	 * @param  CostWageSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostWageSchemeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostWageSchemeSetByTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostWageSchemeSet> queryCostWageSchemeSetByTitleList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostWageSchemeSet queryCostWageSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	
	
	
	public String queryWageName(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	public String queryCostWageList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostWageMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostWageSchemeSetPrint(Map<String,Object> entityMap) throws DataAccessException;
}
