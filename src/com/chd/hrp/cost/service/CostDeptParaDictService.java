/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostDeptParaDict;

/**
* @Title. @Description.
* 分摊参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptParaDictService {

	/**
	 * @Description 
	 * 分摊参数<BR> 添加CostDeptParaDict
	 * @param CostDeptParaDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量添加CostDeptParaDict
	 * @param  CostDeptParaDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptParaDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptParaDict queryCostDeptParaDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 删除CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量删除CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 更新CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量更新CostDeptParaDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
