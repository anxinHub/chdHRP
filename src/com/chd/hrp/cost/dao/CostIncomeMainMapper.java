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
import com.chd.hrp.cost.entity.CostIncome;
import com.chd.hrp.cost.entity.CostIncomeMain;

/**
* @Title. @Description.
* 科室收入数据总表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeMainMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 添加CostIncome
	 * @param CostIncome entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量添加CostIncome
	 * @param  CostIncome entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostIncomeMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncome分页
	 * @param  entityMap RowBounds
	 * @return List<CostIncome>
	 * @throws DataAccessException
	*/
	public List<CostIncomeMain> queryCostIncomeMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncome所有数据
	 * @param  entityMap
	 * @return List<CostIncome>
	 * @throws DataAccessException
	*/
	public List<CostIncomeMain> queryCostIncomeMain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量删除CostIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostIncomeMain(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 按照核算年月删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostIncomeMain(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 科室收入数据总表<BR> 采集数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostIncomeMainByHis(Map<String, Object> entityMap)throws DataAccessException;

}
