/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostIassetTypeArrt;

/**
* @Title. @Description.
* 成本_无形资产分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetTypeArrtService {

	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 添加CostIassetTypeArrt
	 * @param CostIassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量添加CostIassetTypeArrt
	 * @param  CostIassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIassetTypeArrt queryCostIassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String syncCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostIassetTypeArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
