/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostIncomeType;

/**
* @Title. @Description.
* 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeTypeService {

	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 添加CostIncomeType
	 * @param CostIncomeType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量添加CostIncomeType
	 * @param  CostIncomeType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIncomeType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIncomeType queryCostIncomeTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 删除CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量删除CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 更新CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIncomeType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量更新CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIncomeType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
