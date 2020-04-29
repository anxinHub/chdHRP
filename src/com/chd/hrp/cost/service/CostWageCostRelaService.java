/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostWageCostRela;

/**
* @Title. @Description.
* 工资项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageCostRelaService {

	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 添加CostWageCostRela
	 * @param CostWageCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量添加CostWageCostRela
	 * @param  CostWageCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostWageCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostWageCostRela queryCostWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 删除CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量删除CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 更新CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量更新CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 2016/10/25 lxj
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 继承CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException;	
	//打印
	public List<Map<String,Object>> queryCostWageCostRelaPrint(Map<String,Object> entityMap) throws DataAccessException;
}
