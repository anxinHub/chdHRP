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
import com.chd.hrp.cost.entity.CostMaterialDetail;

/**
* @Title. @Description.
* 科室材料支出明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMaterialDetailMapper extends SqlMapper{
	 
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 添加CostMaterialDetail
	 * @param CostMaterialDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量添加CostMaterialDetail
	 * @param  CostMaterialDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMaterialDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 查询CostMaterialDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostMaterialDetail>
	 * @throws DataAccessException
	*/
	public List<CostMaterialDetail> queryCostMaterialDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 查询CostMaterialDetail所有数据
	 * @param  entityMap
	 * @return List<CostMaterialDetail>
	 * @throws DataAccessException
	*/
	public List<CostMaterialDetail> queryCostMaterialDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 查询CostMaterialDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostMaterialDetail queryCostMaterialDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 删除CostMaterialDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量删除CostMaterialDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostMaterialDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 更新CostMaterialDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量更新CostMaterialDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostMaterialDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostMaterialDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 同步数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public void addSynData(Map<String,Object> entityMap)throws DataAccessException;
}
