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
import com.chd.hrp.cost.entity.CostEmpTypeAttr;

/**
* @Title. @Description.
* 成本_职工分类表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpTypeAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 添加CostEmpTypeAttr
	 * @param CostEmpTypeAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量添加CostEmpTypeAttr
	 * @param  CostEmpTypeAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttr分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpTypeAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpTypeAttr> queryCostEmpTypeAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttr所有数据
	 * @param  entityMap
	 * @return List<CostEmpTypeAttr>
	 * @throws DataAccessException
	*/
	public List<CostEmpTypeAttr> queryCostEmpTypeAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpTypeAttr queryCostEmpTypeAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量添加CostEmpTypeAttr
	 * @param  CostEmpTypeAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostEmpTypeAttrNew(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryCostEmpTypeAttrPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
