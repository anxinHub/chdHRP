/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDeptDriData;

/**
* @Title. @Description.
* 科室直接成本表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptDriDataService {

	/**
	 * @Description 
	 * 科室直接成本表<BR> 添加CostDeptDriData
	 * @param CostDeptDriData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量添加CostDeptDriData
	 * @param  CostDeptDriData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptDriData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 查询CostDeptDriData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptDriData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 查询CostDeptDriDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptDriData queryCostDeptDriDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 删除CostDeptDriData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量删除CostDeptDriData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptDriData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 更新CostDeptDriData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量更新CostDeptDriData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptDriData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结余分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public String queryBillingRevenueBalance(Map<String,Object> entityMap) throws DataAccessException; 
	
	/**
	 * @Description 
	 * 诊次成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public String queryVisitsCostAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 床日成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public String queryOnTheBedCostAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public String queryOutpatientVolumeAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public String queryInpatientVolumeAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
}
