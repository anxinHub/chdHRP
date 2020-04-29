/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpTitleAttr;

/**
* @Title. @Description.
* 成本_职工职称字典表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpTitleAttrService {

	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 添加CostEmpTitleAttr
	 * @param CostEmpTitleAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量添加CostEmpTitleAttr
	 * @param  CostEmpTitleAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpTitleAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 查询CostEmpTitleAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpTitleAttr queryCostEmpTitleAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量删除CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpTitleAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_职工职称字典表<BR> 批量更新CostEmpTitleAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpTitleAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String syncCostEmpTypeAttr(Map<String, Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpTitleAttrPrint(Map<String,Object> entityMap) throws DataAccessException;
}
