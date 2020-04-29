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
import com.chd.hrp.cost.entity.CostMateCostRela;

/**
* @Title. @Description.
* 材料类别与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateCostRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 添加CostMateCostRela
	 * @param CostMateCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量添加CostMateCostRela
	 * @param  CostMateCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRela分页
	 * @param  entityMap RowBounds
	 * @return List<CostMateCostRela>
	 * @throws DataAccessException
	*/
	public List<CostMateCostRela> queryCostMateCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRela所有数据
	 * @param  entityMap
	 * @return List<CostMateCostRela>
	 * @throws DataAccessException
	*/
	public List<CostMateCostRela> queryCostMateCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRelaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostMateCostRela queryCostMateCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 删除CostMateCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量删除CostMateCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 更新CostMateCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量更新CostMateCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostMateCostRelaPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
