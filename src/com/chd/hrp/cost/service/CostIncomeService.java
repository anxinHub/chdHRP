/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostIncome;

/**
* @Title. @Description.
* 科室收入数据总表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeService {

	/**
	 * @Description 
	 * 科室收入数据总表<BR> 添加CostIncome
	 * @param CostIncome entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量添加CostIncome
	 * @param  CostIncome entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncome分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncomeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIncome queryCostIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 删除CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量删除CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 更新CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量更新CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
