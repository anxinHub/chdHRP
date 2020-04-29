/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostRiskDetail;

/**
* @Title. @Description.
* 科室提取医疗风险基金表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostRiskDetailService {

	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 添加CostRiskDetail
	 * @param CostRiskDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostRiskDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量添加CostRiskDetail
	 * @param  CostRiskDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostRiskDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 查询CostRiskDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostRiskDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 查询CostRiskDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostRiskDetail queryCostRiskDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 删除CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostRiskDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量删除CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostRiskDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 更新CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostRiskDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量更新CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostRiskDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 资金来源查询<BR> 查询queryCostSource
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostRiskDetail queryCostSource(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostIncomeMainPrint(Map<String,Object> entityMap) throws DataAccessException;
}
