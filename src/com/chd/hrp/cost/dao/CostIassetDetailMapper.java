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
import com.chd.hrp.cost.entity.CostIassetDetail;

/**
* @Title. @Description.
* 科室无形资产折旧明细<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIassetDetailMapper extends SqlMapper{
	 
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 添加CostIassetDetail
	 * @param CostIassetDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量添加CostIassetDetail
	 * @param  CostIassetDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostIassetDetail>
	 * @throws DataAccessException
	*/
	public List<CostIassetDetail> queryCostIassetDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetail所有数据
	 * @param  entityMap
	 * @return List<CostIassetDetail>
	 * @throws DataAccessException
	*/
	public List<CostIassetDetail> queryCostIassetDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostIassetDetail queryCostIassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 删除CostIassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量删除CostIassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 更新CostIassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量更新CostIassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostIassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostIassetDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 同步数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public void addSynData(Map<String,Object> entityMap)throws DataAccessException;
}
