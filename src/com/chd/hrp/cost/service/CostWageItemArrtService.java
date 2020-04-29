/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostWageItemArrt;

/**
* @Title. @Description.
* 成本_工资项属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageItemArrtService {

	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 添加CostWageItemArrt
	 * @param CostWageItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量添加CostWageItemArrt
	 * @param  CostWageItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostWageItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostWageItemArrt queryCostWageItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 删除CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量删除CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 更新CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量更新CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_工资项属性表<BR> 同步财务工资项到成本工资项
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;

	String syncCostWageItemArrtNew(Map<String, Object> mapVo);
	//打印
	public List<Map<String,Object>> queryCostWageItemArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
