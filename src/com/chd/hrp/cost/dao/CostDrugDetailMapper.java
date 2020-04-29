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
import com.chd.hrp.cost.entity.CostDrugDetail;

/**
* @Title. @Description.
* 科室药品费用表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDrugDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 添加CostDrugDetail
	 * @param CostDrugDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量添加CostDrugDetail
	 * @param  CostDrugDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostDrugDetail>
	 * @throws DataAccessException
	*/
	public List<CostDrugDetail> queryCostDrugDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetail所有数据
	 * @param  entityMap
	 * @return List<CostDrugDetail>
	 * @throws DataAccessException
	*/
	public List<CostDrugDetail> queryCostDrugDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDrugDetail queryCostDrugDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 删除CostDrugDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量删除CostDrugDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室药品费用表<BR> 更新CostDrugDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量更新CostDrugDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDrugDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostDrugDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
