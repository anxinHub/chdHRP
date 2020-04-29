/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostDeptNature;

/**
* @Title. @Description.
* 成本习性(01 固定 02 变动)

<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptNatureService {

	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 添加CostDeptNature
	 * @param CostDeptNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量添加CostDeptNature
	 * @param  CostDeptNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNature分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 查询CostDeptNatureByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptNature queryCostDeptNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 删除CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量删除CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 更新CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本习性(01 固定 02 变动)

<BR> 批量更新CostDeptNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
