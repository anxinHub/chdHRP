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
import com.chd.hrp.cost.entity.CostEmpTitleAttr;

/**
* @Title. @Description.
* 成本_职工职称字典表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpTitleAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 添加CostEmpTitleAttr
	 * @param CostEmpTitleAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量添加CostEmpTitleAttr
	 * @param  CostEmpTitleAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttr分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpTitleAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpTitleAttr> queryCostEmpTitleAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttr所有数据
	 * @param  entityMap
	 * @return List<CostEmpTitleAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpTitleAttr> queryCostEmpTitleAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpTitleAttr queryCostEmpTitleAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpTitleAttrPrint(
			Map<String, Object> entityMap) throws DataAccessException;
}
