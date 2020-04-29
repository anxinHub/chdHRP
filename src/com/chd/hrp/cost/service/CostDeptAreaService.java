/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDeptArea;

/**
* @Title. @Description.
* 科室面积表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptAreaService {

	/**
	 * @Description 
	 * 科室面积表<BR> 添加CostDeptArea
	 * @param CostDeptArea entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量添加CostDeptArea
	 * @param  CostDeptArea entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 查询CostDeptArea分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptArea(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 查询CostDeptAreaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptArea queryCostDeptAreaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 删除CostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 按月删除CostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量删除CostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 更新CostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 科室面积表<BR> 继承extendCostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostDeptArea(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室面积表<BR> 批量更新CostDeptArea
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptArea(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostDeptAreaPrint(Map<String,Object> entityMap) throws DataAccessException;
}
