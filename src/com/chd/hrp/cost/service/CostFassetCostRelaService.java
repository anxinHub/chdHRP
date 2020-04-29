/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostFassetCostRela;

/**
* @Title. @Description.
* 固定资产分类与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetCostRelaService {

	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 添加CostFassetCostRela
	 * @param CostFassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量添加CostFassetCostRela
	 * @param  CostFassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostFassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostFassetCostRela queryCostFassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 删除CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量删除CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 更新CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量更新CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 2016/10/26 lxj
	 * 固定资产分类与成本项目的对应关系<BR> 继承CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostFassetArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
