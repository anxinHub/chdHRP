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

import com.chd.hrp.cost.entity.CostDeptNature;

/**
* @Title. @Description.
* 成本习性(01 固定 02 变动)

<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptNatureMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 添加CostDeptNature
	 * @param CostDeptNature entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量添加CostDeptNature
	 * @param  CostDeptNature entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNature分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptNature>
	 * @throws DataAccessException
	*/
	public List<CostDeptNature> queryCostDeptNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNature所有数据
	 * @param  entityMap
	 * @return List<CostDeptNature>
	 * @throws DataAccessException
	*/
	public List<CostDeptNature> queryCostDeptNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNatureByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptNature queryCostDeptNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 删除CostDeptNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量删除CostDeptNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 更新CostDeptNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量更新CostDeptNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
}
