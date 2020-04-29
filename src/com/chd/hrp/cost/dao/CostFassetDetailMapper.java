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
import com.chd.hrp.cost.entity.CostFassetDetail;

/**
* @Title. @Description.
* 科室固定资产折旧明细<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetDetailMapper extends SqlMapper{
	  
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 添加CostFassetDetail
	 * @param CostFassetDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量添加CostFassetDetail
	 * @param  CostFassetDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostFassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 查询CostFassetDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostFassetDetail>
	 * @throws DataAccessException
	*/
	public List<CostFassetDetail> queryCostFassetDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 查询CostFassetDetail所有数据
	 * @param  entityMap
	 * @return List<CostFassetDetail>
	 * @throws DataAccessException
	*/
	public List<CostFassetDetail> queryCostFassetDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 查询CostFassetDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostFassetDetail queryCostFassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 删除CostFassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量删除CostFassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostFassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 更新CostFassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量更新CostFassetDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostFassetDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostFassetDetailPrint(
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
