/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostFassetArrt;

/**
* @Title. @Description.
* 成本_固定资产字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetArrtService {

	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 添加CostFassetArrt
	 * @param CostFassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量添加CostFassetArrt
	 * @param  CostFassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostFassetArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostFassetArrt queryCostFassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 删除CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量删除CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 更新CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量更新CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String syncCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;

	public String syncCostFassetArrtNew(Map<String, Object> mapVo)throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryCostFassetArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
