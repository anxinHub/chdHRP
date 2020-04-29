/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostWageScheme;
import com.chd.hrp.cost.entity.CostWageSchemeSet;

/**
* @Title. @Description.
* 职工工资查询方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageSchemeService {

	/**
	 * @Description 
	 * 职工工资查询方案<BR> 添加CostWageScheme
	 * @param CostWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量添加CostWageScheme
	 * @param  CostWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageScheme分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostWageScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageSchemeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostWageScheme queryCostWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 删除CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量删除CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 更新CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量更新CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostWageScheme CostWageSequence()throws DataAccessException;
	
}
