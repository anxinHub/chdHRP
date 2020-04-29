/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostFassetTypeArrt;

/**
* @Title. @Description.
* 成本_固定资产分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetTypeArrtService {

	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 添加CostFassetTypeArrt
	 * @param CostFassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量添加CostFassetTypeArrt
	 * @param  CostFassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostFassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostFassetTypeArrt queryCostFassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/31 lxj 
	 * 成本_固定资产分类字典<BR> 同步
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 2016/10/31 lxj 
	 * 成本_固定资产分类字典<BR> 同步
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostFassetTypeArrtFNew(Map<String, Object> mapVo) throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostFassetTypeArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
}
