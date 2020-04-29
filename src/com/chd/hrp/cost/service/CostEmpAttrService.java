/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpAttr;

/**
* @Title. @Description.
* 成本_职工字典表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpAttrService {

	/**
	 * @Description 
	 * 成本_职工字典表<BR> 添加CostEmpAttr
	 * @param CostEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量添加CostEmpAttr
	 * @param  CostEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpAttr queryCostEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 删除CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量删除CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 更新CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量更新CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统平台职工表<BR> 查询queryEmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpAttr queryEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpAttrPrint(Map<String,Object> entityMap) throws DataAccessException;
}
