/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostItemDict;

/**
* @Title. @Description.
* 成本项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostItemDictService {

	/**
	 * @Description 
	 * 成本项目字典<BR> 添加CostItemDict
	 * @param CostItemDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 批量添加CostItemDict
	 * @param  CostItemDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 查询CostItemDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostItemDict(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本项目字典<BR> 查询CostItemDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public List<?> queryCostItemDictByTree(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 查询CostItemDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostItemDict queryCostItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 成本项目字典<BR> 查询CostItemDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public CostItemDict queryCostItemDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 删除CostItemDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 批量删除CostItemDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 更新CostItemDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目字典<BR> 批量更新CostItemDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateCostItemBatch(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostItemDictPrint(Map<String,Object> entityMap) throws DataAccessException;
}
