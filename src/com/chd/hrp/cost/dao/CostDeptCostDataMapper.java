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
import com.chd.hrp.cost.entity.CostDeptCostData;
import com.chd.hrp.cost.entity.CostStructureAnalysis;

/**
* @Title. @Description.
* 科室成本核算表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptCostDataMapper extends SqlMapper{
	
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 批量添加CostDeptCostData
	 * @param  CostDeptCostData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptCostData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 查询CostDeptCostData分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptCostData>
	 * @throws DataAccessException
	*/
	public List<CostDeptCostData> queryCostDeptCostData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室成本核算表<BR> 查询CostDeptCostData所有数据
	 * @param  entityMap
	 * @return List<CostDeptCostData>
	 * @throws DataAccessException
	*/
	public List<CostDeptCostData> queryCostDeptCostData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 查询CostDeptCostDataByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptCostData queryCostDeptCostDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 删除CostDeptCostData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptCostData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 批量删除CostDeptCostData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptCostData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本构成分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostStructureAnalysis> queryCostStructureAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本构成分析
	 * @param  entityMap RowBounds
	 * @return List<CostBalanceAnalysis>
	 * @throws DataAccessException
	*/
	public List<CostStructureAnalysis> queryCostStructureAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
