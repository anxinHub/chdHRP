/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostMonthEnd;

/**
* @Title. @Description.
* 科室成本核算月结表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMonthEndService {

	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 添加CostMonthEnd
	 * @param CostMonthEnd entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量添加CostMonthEnd
	 * @param  CostMonthEnd entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEnd分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostMonthEnd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEndByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostMonthEnd queryCostMonthEndByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 删除CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量删除CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 更新CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量更新CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostMonthEnd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 获取 成本结账相关信息<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public String queryCostYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 取出当前成本的未结账最小会计期间<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCostCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 结转<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
    public String updateCostFinalCharge(Map<String, Object> entityMap) throws DataAccessException ;
    
	/**
	 * @Description 反结<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
    public String updateCostFinalUnCharge(Map<String, Object> entityMap) throws DataAccessException ;
    
    /**
	 * @Description 
	 * 生成成本分析报表
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public String saveCostAnalysisProc(Map<String,Object> entityMap) throws DataAccessException;
	
}
