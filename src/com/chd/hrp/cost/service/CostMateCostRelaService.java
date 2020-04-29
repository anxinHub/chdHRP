/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostMateCostRela;

/**
* @Title. @Description.
* 材料类别与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateCostRelaService {

	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 添加CostMateCostRela
	 * @param CostMateCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量添加CostMateCostRela
	 * @param  CostMateCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostMateCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostMateCostRela queryCostMateCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 删除CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量删除CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 更新CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量更新CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostMateCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/26 lxj
	 * 材料类别与成本项目的对应关系<BR> 继承CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryCostMateCostRelaPrint(Map<String,Object> entityMap) throws DataAccessException;
}
