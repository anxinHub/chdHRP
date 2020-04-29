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
import com.chd.hrp.cost.entity.CostEmpAttr;

/**
* @Title. @Description.
* 成本_职工字典表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 添加CostEmpAttr
	 * @param CostEmpAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量添加CostEmpAttr
	 * @param  CostEmpAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpAttr> queryCostEmpAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttr所有数据
	 * @param  entityMap
	 * @return List<CostEmpAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpAttr> queryCostEmpAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpAttr queryCostEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public CostEmpAttr queryEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 删除CostEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量删除CostEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 更新CostEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量更新CostEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpAttrPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
