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
import com.chd.hrp.cost.entity.CostMonthEnd;

/**
* @Title. @Description.
* 科室成本核算月结表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMonthEndMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 添加CostMonthEnd
	 * @param CostMonthEnd entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量添加CostMonthEnd
	 * @param  CostMonthEnd entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEnd分页
	 * @param  entityMap RowBounds
	 * @return List<CostMonthEnd>
	 * @throws DataAccessException
	*/
	public List<CostMonthEnd> queryCostMonthEnd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	public List<CostMonthEnd> queryLastCostMonthEnd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEnd所有数据
	 * @param  entityMap
	 * @return List<CostMonthEnd>
	 * @throws DataAccessException
	*/
	public List<CostMonthEnd> queryCostMonthEnd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEndByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryCostYearMonthByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 删除CostMonthEnd
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量删除CostMonthEnd
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 更新CostMonthEnd
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description  
	 * 科室成本核算月结表<BR> 批量更新CostMonthEnd
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 获取 成本结账相关信息<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<?> queryCostYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 取出当前成本的未结账最小会计期间<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryCostCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @param <T>
	 * @Description 
	 * 月末结转<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public int updateCostFinalCharge(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 月末反结<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public int updateCostFinalUnCharge(Map<String, Object> entityMap)throws DataAccessException;

	public void deleteCost(Map<String, Object> entityMap)throws DataAccessException;
}
