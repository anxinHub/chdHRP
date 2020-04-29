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
import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;
import com.chd.hrp.cost.entity.HrpCostSelect;

/**
* @Title. @Description.
* 成本_职工分类奖金项配置表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpKindBonusItemSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 添加CostEmpKindBonusItemSet
	 * @param CostEmpKindBonusItemSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量添加CostEmpKindBonusItemSet
	 * @param  CostEmpKindBonusItemSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpKindBonusItemSet>
	 * @throws DataAccessException
	*/
	public List<CostEmpKindBonusItemSet> queryCostEmpKindBonusItemSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSet所有数据
	 * @param  entityMap
	 * @return List<CostEmpKindBonusItemSet>
	 * @throws DataAccessException
	*/
	public List<CostEmpKindBonusItemSet> queryCostEmpKindBonusItemSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpKindBonusItemSet queryCostEmpKindBonusItemSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpKindBonusItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpBonusList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpBonusMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpBonusItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpBonusItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询记录数queryRecordNumByBatchCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryRecordNumByBatchCode(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 通过职工分类查询奖金项
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<CostEmpKindBonusItemSet> queryCostBonusCostRelaByEmpKindCode(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpKindBonusItemSetPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
