/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBonusItemArrt;

/**
* @Title. @Description.
* 成本_奖金项属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusItemArrtService {

	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 添加CostBonusItemArrt
	 * @param CostBonusItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量添加CostBonusItemArrt
	 * @param  CostBonusItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostBonusItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostBonusItemArrt queryCostBonusItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostBonusItemArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
