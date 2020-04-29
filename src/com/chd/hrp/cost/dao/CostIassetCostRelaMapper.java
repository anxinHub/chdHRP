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
import com.chd.hrp.cost.entity.CostIassetCostRela;

/**
* @Title. @Description.
* 无形资产分类与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetCostRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 添加CostIassetCostRela
	 * @param CostIassetCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量添加CostIassetCostRela
	 * @param  CostIassetCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return List<CostIassetCostRela>
	 * @throws DataAccessException
	*/
	public List<CostIassetCostRela> queryCostIassetCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRela所有数据
	 * @param  entityMap
	 * @return List<CostIassetCostRela>
	 * @throws DataAccessException
	*/
	public List<CostIassetCostRela> queryCostIassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRelaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIassetCostRela queryCostIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 删除CostIassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量删除CostIassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 更新CostIassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量更新CostIassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostIassetCostRelaPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
