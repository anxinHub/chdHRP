/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpTypeAttr;

/**
* @Title. @Description.
* 成本_职工分类表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpTypeAttrService {

	/**
	 * @Description 
	 * 成本_职工分类表<BR> 添加CostEmpTypeAttr
	 * @param CostEmpTypeAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量添加CostEmpTypeAttr
	 * @param  CostEmpTypeAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpTypeAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 查询CostEmpTypeAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpTypeAttr queryCostEmpTypeAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量删除CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpTypeAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工分类表<BR> 批量更新CostEmpTypeAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpTypeAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	

/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_职工分类表<BR> 同步
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostEmpTypeAttr(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_职工分类表<BR> 同步
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostEmpTypeAttrNew(Map<String, Object> mapVo) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpTypeAttrPrint(Map<String,Object> entityMap) throws DataAccessException;
}
