/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostInhosWork;

/**
* @Title. @Description.
* 住院工作量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInhosWorkService {

	/**
	 * @Description 
	 * 住院工作量表<BR> 添加CostInhosWork
	 * @param CostInhosWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量添加CostInhosWork
	 * @param  CostInhosWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWork分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostInhosWork(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWorkByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostInhosWork queryCostInhosWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 删除CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量删除CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量删除CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 更新CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量更新CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostInhosWorkPrint(Map<String,Object> entityMap) throws DataAccessException;
}
