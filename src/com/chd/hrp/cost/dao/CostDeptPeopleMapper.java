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
import com.chd.hrp.cost.entity.CostDeptPeople;

/**
* @Title. @Description.
* 科室人员表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptPeopleMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室人员表<BR> 添加CostDeptPeople
	 * @param CostDeptPeople entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量添加CostDeptPeople
	 * @param  CostDeptPeople entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeople分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptPeople>
	 * @throws DataAccessException
	*/
	public List<CostDeptPeople> queryCostDeptPeople(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeople所有数据
	 * @param  entityMap
	 * @return List<CostDeptPeople>
	 * @throws DataAccessException
	*/
	public List<CostDeptPeople> queryCostDeptPeople(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeopleByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptPeople queryCostDeptPeopleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 删除CostDeptPeople
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量删除CostDeptPeople
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 按月刪除CostDeptPeople
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室人员表<BR> 更新CostDeptPeople
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量更新CostDeptPeople
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDeptPeople(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室人员表<BR> 采集数据
	 * @param  entityMap  
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPeopleCollect(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 科室人员表<BR> 继承
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int extendCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<Map<String, Object>> queryCostDeptPeoplePrint(Map<String, Object> entityMap)throws DataAccessException;
}
