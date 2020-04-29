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

import com.chd.hrp.cost.entity.CostIncomeType;

/**
* @Title. @Description.
* 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 添加CostIncomeType
	 * @param CostIncomeType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量添加CostIncomeType
	 * @param  CostIncomeType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeType分页
	 * @param  entityMap RowBounds
	 * @return List<CostIncomeType>
	 * @throws DataAccessException
	*/
	public List<CostIncomeType> queryCostIncomeType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeType所有数据
	 * @param  entityMap
	 * @return List<CostIncomeType>
	 * @throws DataAccessException
	*/
	public List<CostIncomeType> queryCostIncomeType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIncomeType queryCostIncomeTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 删除CostIncomeType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量删除CostIncomeType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 更新CostIncomeType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量更新CostIncomeType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
}
