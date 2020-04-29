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
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.entity.HrpCostSelect;

/**
* @Title. @Description.
* 职工工资查询方案表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageSchemeSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 添加CostWageSchemeSet
	 * @param CostWageSchemeSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量添加CostWageSchemeSet
	 * @param  CostWageSchemeSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询queryCostWageSchemeName
	 * @param  entityList RowBounds
	 * @return List<CostWageSchemeSet>
	 * @throws DataAccessException
	*/
	public CostWageSchemeSet  queryCostWageSchemeName(Map<String, Object> entityList) throws DataAccessException;
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostWageSchemeSet>
	 * @throws DataAccessException
	*/
	public List<CostWageSchemeSet> queryCostWageSchemeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSet所有数据
	 * @param  entityMap
	 * @return List<CostWageSchemeSet>
	 * @throws DataAccessException
	*/
	public List<CostWageSchemeSet> queryCostWageSchemeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostWageSchemeSet> queryCostWageSchemeSetByTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageSchemeSet queryCostWageSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<CostWageSchemeSet> queryWageName(Map<String, Object> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostWageSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	public List<HrpCostSelect> queryCostWageList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostWageMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostWageItemList(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostWageSchemeSetPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
