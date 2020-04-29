/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostInAcctVouch;

/**
* @Title. @Description.
* 科室收入总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInAcctVouchService {

	/**
	 * @Description 
	 * 科室收入总账<BR> 添加CostInAcctVouch
	 * @param CostInAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量添加CostInAcctVouch
	 * @param  CostInAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostInAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostInAcctVouch queryCostInAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 删除CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量删除CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 更新CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量更新CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostInAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
