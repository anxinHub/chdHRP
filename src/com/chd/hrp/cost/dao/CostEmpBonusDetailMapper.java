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
import com.chd.hrp.cost.entity.CostEmpBonusDetail;

/**
* @Title. @Description.
* 人员奖金明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpBonusDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 添加CostEmpBonusDetail
	 * @param CostEmpBonusDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量添加CostEmpBonusDetail
	 * @param  CostEmpBonusDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpBonusDetail>
	 * @throws DataAccessException
	*/
	public List<CostEmpBonusDetail> queryCostEmpBonusDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetail所有数据
	 * @param  entityMap
	 * @return List<CostEmpBonusDetail>
	 * @throws DataAccessException
	*/
	public List<CostEmpBonusDetail> queryCostEmpBonusDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpBonusDetail queryCostEmpBonusDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpBonusDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
