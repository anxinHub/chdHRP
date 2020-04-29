/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostChargeKindArrt;

/**
* @Title. @Description.
* 成本_收费类别字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostChargeKindArrtService {

	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 添加CostChargeKindArrt
	 * @param CostChargeKindArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量添加CostChargeKindArrt
	 * @param  CostChargeKindArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostChargeKindArrt(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryCostChargeKindArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostChargeKindArrt queryCostChargeKindArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostChargeKindArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
