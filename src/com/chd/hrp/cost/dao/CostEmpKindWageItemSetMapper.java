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
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.entity.HrpCostSelect;

/**
* @Title. @Description.
* 成本_职工分类工资项配置表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpKindWageItemSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 添加CostEmpKindWageItemSet
	 * @param CostEmpKindWageItemSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量添加CostEmpKindWageItemSet
	 * @param  CostEmpKindWageItemSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpKindWageItemSet>
	 * @throws DataAccessException
	*/
	public List<CostEmpKindWageItemSet> queryCostEmpKindWageItemSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSet所有数据
	 * @param  entityMap
	 * @return List<CostEmpKindWageItemSet>
	 * @throws DataAccessException
	*/
	public List<CostEmpKindWageItemSet> queryCostEmpKindWageItemSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpKindWageItemSet queryCostEmpKindWageItemSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpKindWageItemSet(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<HrpCostSelect> queryCostEmpWageList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpWageMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HrpCostSelect> queryCostEmpWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/27 lxj
	 * 成本_职工分类工资项配置表<BR> 按条件查询数据数量CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryCostEmpKindWageItemSetByBatchCode(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/27 sjy
	 * 通过职工分类查询工资项编码 queryCostEmpKindWageItemSetByEmpKindCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	
	public List<CostEmpKindWageItemSet> queryCostEmpKindWageItemSetByEmpKindCode(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpKindWageItemSetPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
