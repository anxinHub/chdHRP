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
import com.chd.hrp.cost.entity.CostBonusCostRela;

/**
* @Title. @Description.
* 奖金项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusCostRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 添加CostBonusCostRela
	 * @param CostBonusCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量添加CostBonusCostRela
	 * @param  CostBonusCostRela entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRela分页
	 * @param  entityMap RowBounds
	 * @return List<CostBonusCostRela>
	 * @throws DataAccessException
	*/
	public List<CostBonusCostRela> queryCostBonusCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRela所有数据
	 * @param  entityMap
	 * @return List<CostBonusCostRela>
	 * @throws DataAccessException
	*/
	public List<CostBonusCostRela> queryCostBonusCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRelaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusCostRela queryCostBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 删除CostBonusCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量删除CostBonusCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 更新CostBonusCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量更新CostBonusCostRela
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostBonusCostRelaPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
