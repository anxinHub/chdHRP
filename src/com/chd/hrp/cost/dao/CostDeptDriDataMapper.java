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
import com.chd.hrp.cost.entity.CostBalanceAnalysis;
import com.chd.hrp.cost.entity.CostCostAnalysis;
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.entity.CostVolumeAnalysis;

/**
* @Title. @Description.
* 科室直接成本表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptDriDataMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 添加CostDeptDriData
	 * @param CostDeptDriData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量添加CostDeptDriData
	 * @param  CostDeptDriData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptDriData(List entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 查询CostDeptDriData分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptDriData>
	 * @throws DataAccessException
	*/
	public List<CostDeptDriData> queryCostDeptDriData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室直接成本表<BR> 查询CostDeptDriData所有数据
	 * @param  entityMap
	 * @return List<CostDeptDriData>
	 * @throws DataAccessException
	*/
	public List<CostDeptDriData> queryCostDeptDriData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 查询CostDeptDriDataByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptDriData queryCostDeptDriDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 删除CostDeptDriData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量删除CostDeptDriData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptDriData(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室直接成本表<BR> 更新CostDeptDriData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDeptDriData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室直接成本表<BR> 批量更新CostDeptDriData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDeptDriData(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 科室成本核算表<BR> 查询科室支出总账
	 * @param  entityMap RowBounds
	 * @return List<CostDeptCostData>
	 * @throws DataAccessException
	*/
	public List<CostDeptDriData> queryCostOutVouchData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结余分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostBalanceAnalysis> queryBillingRevenueBalance(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结余分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostBalanceAnalysis> queryBillingRevenueBalance(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 诊次成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostCostAnalysis> queryVisitsCostAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 诊次成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostCostAnalysis> queryVisitsCostAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 床日成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostCostAnalysis> queryOnTheBedCostAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 床日成本分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostCostAnalysis> queryOnTheBedCostAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostVolumeAnalysis> queryOutpatientVolumeAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostVolumeAnalysis> queryOutpatientVolumeAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostVolumeAnalysis> queryInpatientVolumeAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院量本利分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostVolumeAnalysis> queryInpatientVolumeAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室直接成本表 为分摊做准备
	 * @param  entityMap
	 * @return List<CostDeptDriData>
	 * @throws DataAccessException
	*/
	public List<CostDeptDriData> queryCostDeptDriDataByShare(Map<String,Object> entityMap) throws DataAccessException;
	
}
