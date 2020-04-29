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
import com.chd.hrp.cost.entity.CostWageCostRela;

/**
* @Title. @Description.
* 工资项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageCostRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 添加CostWageCostRela
	 * @param CostWageCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量添加CostWageCostRela
	 * @param  CostWageCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRela分页
	 * @param  entityMap RowBounds
	 * @return List<CostWageCostRela>
	 * @throws DataAccessException
	*/
	public List<CostWageCostRela> queryCostWageCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRela所有数据
	 * @param  entityMap
	 * @return List<CostWageCostRela>
	 * @throws DataAccessException
	*/
	public List<CostWageCostRela> queryCostWageCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRelaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageCostRela queryCostWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 删除CostWageCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量删除CostWageCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 更新CostWageCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量更新CostWageCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostWageCostRelaPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
