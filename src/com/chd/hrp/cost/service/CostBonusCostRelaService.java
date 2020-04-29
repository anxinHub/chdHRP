/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBonusCostRela;

/**
* @Title. @Description.
* 奖金项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusCostRelaService {

	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 添加CostBonusCostRela
	 * @param CostBonusCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量添加CostBonusCostRela
	 * @param  CostBonusCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostBonusCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostBonusCostRela queryCostBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 删除CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量删除CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 更新CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量更新CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostBonusCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 2016/10/26 lxj 
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 继承CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostBonusCostRelaPrint(Map<String,Object> entityMap) throws DataAccessException;
}
