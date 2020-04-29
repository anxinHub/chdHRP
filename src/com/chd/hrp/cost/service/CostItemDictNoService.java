/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostItemDictNo;

/**
* @Title. @Description.
* 成本项目变更表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostItemDictNoService {

	/**
	 * @Description 
	 * 成本项目变更表<BR> 添加CostItemDictNo
	 * @param HtcCostItemDictNo entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量添加CostItemDictNo
	 * @param  HtcCostItemDictNo entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostItemDictNo> queryItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNoByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostItemDictNo queryCostItemDictNoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 删除CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量删除CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 更新CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量更新CostItemDictNo
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateCostItemNoBatch(Map<String, Object> entityMap) throws DataAccessException;
	
}
