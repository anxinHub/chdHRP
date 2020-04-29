/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostIassetDetail;

/**
* @Title. @Description.
* 科室无形资产折旧明细<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetDetailService {
 
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 添加CostIassetDetail
	 * @param CostIassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量添加CostIassetDetail
	 * @param  CostIassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIassetDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostIassetDetail queryCostIassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 删除CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量删除CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 更新CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量更新CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostIassetDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 同步数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSynData(Map<String,Object> entityMap)throws DataAccessException;
}
