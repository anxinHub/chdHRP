/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostChargeItemArrt;

/**
* @Title. @Description.
* 成本_收费项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostChargeItemArrtService {

	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 添加CostChargeItemArrt
	 * @param CostChargeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量添加CostChargeItemArrt
	 * @param  CostChargeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostChargeItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryCostChargeItemArrtByKindCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostChargeItemArrt queryCostChargeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostChargeItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostChargeItemArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 导入
	 * 
	 * */
	public String costChargeItemArrtImportPage(Map<String, Object> entityMap) throws DataAccessException;
}
