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
import com.chd.hrp.cost.entity.CostIncomeDetail;

/**
* @Title. @Description.
* 科室收入数据明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 添加CostIncomeDetail
	 * @param CostIncomeDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 批量添加CostIncomeDetail
	 * @param  CostIncomeDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIncomeDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 查询CostIncomeDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostIncomeDetail>
	 * @throws DataAccessException
	*/
	public List<CostIncomeDetail> queryCostIncomeDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 查询CostIncomeDetail所有数据
	 * @param  entityMap
	 * @return List<CostIncomeDetail>
	 * @throws DataAccessException
	*/
	public List<CostIncomeDetail> queryCostIncomeDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 批量删除CostIncomeDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIncomeDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 采集数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeDetailByHis(Map<String, Object> entityMap)throws DataAccessException;
  
}
