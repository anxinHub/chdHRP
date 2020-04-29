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
import com.chd.hrp.cost.entity.CostFassetCostRela;

/**
* @Title. @Description.
* 固定资产分类与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetCostRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 添加CostFassetCostRela
	 * @param CostFassetCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量添加CostFassetCostRela
	 * @param  CostFassetCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return List<CostFassetCostRela>
	 * @throws DataAccessException
	*/
	public List<CostFassetCostRela> queryCostFassetCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRela所有数据
	 * @param  entityMap
	 * @return List<CostFassetCostRela>
	 * @throws DataAccessException
	*/
	public List<CostFassetCostRela> queryCostFassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRelaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostFassetCostRela queryCostFassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 删除CostFassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量删除CostFassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 更新CostFassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量更新CostFassetCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostFassetCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostFassetArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
