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
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.entity.HrpCostSelect;

/**
* @Title. @Description.
* 职工奖金查询方案表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusSchemeSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 添加CostBonusSchemeSet
	 * @param CostBonusSchemeSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量添加CostBonusSchemeSet
	 * @param  CostBonusSchemeSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostBonusSchemeSet>
	 * @throws DataAccessException
	*/
	public List<CostBonusSchemeSet> queryCostBonusSchemeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSet所有数据
	 * @param  entityMap
	 * @return List<CostBonusSchemeSet>
	 * @throws DataAccessException
	*/
	public List<CostBonusSchemeSet> queryCostBonusSchemeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostBonusSchemeSet> queryCostBonusSchemeSetByTitle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusSchemeSet queryCostBonusSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询queryCostWageSchemeName
	 * @param  entityList RowBounds
	 * @return List<CostWageSchemeSet>
	 * @throws DataAccessException
	*/
	public CostBonusSchemeSet  queryCostBonusSchemeName(Map<String, Object> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostBonusSchemeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<HrpCostSelect> queryCostBonusList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostBonusMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostBonusItemList(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostBonusSchemeSetPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
