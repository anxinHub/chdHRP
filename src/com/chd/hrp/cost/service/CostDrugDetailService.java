/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDrugDetail;

/**
* @Title. @Description.
* 科室药品费用表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDrugDetailService {

	/**
	 * @Description 
	 * 科室药品费用表<BR> 添加CostDrugDetail
	 * @param CostDrugDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量添加CostDrugDetail
	 * @param  CostDrugDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDrugDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDrugDetail queryCostDrugDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 删除CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量删除CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 更新CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量更新CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostDrugDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
}
