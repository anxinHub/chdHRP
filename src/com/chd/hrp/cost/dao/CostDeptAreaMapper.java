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
import com.chd.hrp.cost.entity.CostDeptArea;

/**
* @Title. @Description.
* 科室面积表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptAreaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室面积表<BR> 添加CostDeptArea
	 * @param CostDeptArea entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量添加CostDeptArea
	 * @param  CostDeptArea entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 查询CostDeptArea分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptArea>
	 * @throws DataAccessException
	*/
	public List<CostDeptArea> queryCostDeptArea(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室面积表<BR> 查询CostDeptArea所有数据
	 * @param  entityMap
	 * @return List<CostDeptArea>
	 * @throws DataAccessException
	*/
	public List<CostDeptArea> queryCostDeptArea(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 查询CostDeptAreaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptArea queryCostDeptAreaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 删除CostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 按月删除CostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量删除CostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室面积表<BR> 更新CostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量更新CostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;


	/**
	 * @Description 
	 * 科室面积表<BR> 继承extendCostDeptArea
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int extendCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<Map<String, Object>> queryCostDeptAreaPrint(Map<String, Object> entityMap)throws DataAccessException;
}
