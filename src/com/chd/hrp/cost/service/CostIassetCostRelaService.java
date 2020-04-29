/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostIassetCostRela;

/**
* @Title. @Description.
* 无形资产分类与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetCostRelaService {

	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 添加CostIassetCostRela
	 * @param CostIassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量添加CostIassetCostRela
	 * @param  CostIassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIassetCostRela queryCostIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 删除CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量删除CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 更新CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量更新CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 2016/10/26 lxj
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 继承CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostIassetCostRelaPrint(Map<String,Object> entityMap) throws DataAccessException;
}
