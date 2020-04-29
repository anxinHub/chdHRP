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
import com.chd.hrp.cost.entity.CostItemDictNo;

/**
* @Title. @Description.
* 成本项目变更表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostItemDictNoMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 添加CostItemDictNo
	 * @param HtcCostItemDictNo entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量添加CostItemDictNo
	 * @param  HtcCostItemDictNo entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return List<CostItemDictNo>
	 * @throws DataAccessException
	*/
	public List<CostItemDictNo> queryCostItemDictNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo所有数据
	 * @param  entityMap
	 * @return List<CostItemDictNo>
	 * @throws DataAccessException
	*/
	public List<CostItemDictNo> queryCostItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo所有数据
	 * @param  entityMap
	 * @return List<CostItemDictNo>
	 * @throws DataAccessException
	*/
	public List<CostItemDictNo> queryItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNoByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostItemDictNo queryCostItemDictNoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 删除CostItemDictNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量删除CostItemDictNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本项目变更表<BR> 更新CostItemDictNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostItemDictNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateCostItemDictNoState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 批量更新CostItemDictNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostItemDictNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateCostItemNoBatch(Map<String, Object> entityMap) throws DataAccessException;
}
