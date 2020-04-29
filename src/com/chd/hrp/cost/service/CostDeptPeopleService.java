/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDeptPeople;

/**
* @Title. @Description.
* 科室人员表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptPeopleService {

	/**
	 * @Description 
	 * 科室人员表<BR> 添加CostDeptPeople
	 * @param CostDeptPeople entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量添加CostDeptPeople
	 * @param  CostDeptPeople entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeople分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptPeople(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeopleByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptPeople queryCostDeptPeopleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 删除CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量删除CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 按月CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 科室人员表<BR> 更新CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量更新CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description  
	 * 科室人员表<BR> 采集数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addPeopleCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 继承
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String  extendCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<Map<String,Object>> queryCostDeptPeoplePrint(Map<String,Object> entityMap) throws DataAccessException;
}
