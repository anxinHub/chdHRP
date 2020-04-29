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

import com.chd.hrp.cost.entity.CostInAcctVouch;

/**
* @Title. @Description.
* 科室收入总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInAcctVouchMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 添加CostInAcctVouch
	 * @param CostInAcctVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量添加CostInAcctVouch
	 * @param  CostInAcctVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return List<CostInAcctVouch>
	 * @throws DataAccessException
	*/
	public List<CostInAcctVouch> queryCostInAcctVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouch所有数据
	 * @param  entityMap
	 * @return List<CostInAcctVouch>
	 * @throws DataAccessException
	*/
	public List<CostInAcctVouch> queryCostInAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouchByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostInAcctVouch queryCostInAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 删除CostInAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量删除CostInAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室收入总账<BR> 更新CostInAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量更新CostInAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
}
