/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostMateTypeArrt;

/**
* @Title. @Description.
* 成本_材料分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateTypeArrtService {

	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 添加CostMateTypeArrt
	 * @param CostMateTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量添加CostMateTypeArrt
	 * @param  CostMateTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 查询CostMateTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostMateTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 查询CostMateTypeArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostMateTypeArrt queryCostMateTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 删除CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量删除CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 更新CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量更新CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description
	 * 2016/10/31 lxj 
	 * 成本_材料分类字典<BR> 同步CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description
	 * 2016/10/31 lxj 
	 * 成本_材料分类字典<BR> 同步CostMateTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostMateTypeArrtNew(Map<String, Object> entityMap) throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryCostMateTypeArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
