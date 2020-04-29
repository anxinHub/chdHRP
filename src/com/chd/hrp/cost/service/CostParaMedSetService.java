/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostParaAssSet;
import com.chd.hrp.cost.entity.CostParaMedSet;

/**
* @Title. @Description.
* 医技分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaMedSetService {

	/**
	 * @Description 
	 * 医技分摊设置<BR> 添加CostParaMedSet
	 * @param CostParaMedSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量添加CostParaMedSet
	 * @param  CostParaMedSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaMedSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaMedSet queryCostParaMedSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 删除CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量删除CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 更新CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量更新CostParaMedSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostParaMedSet> queryCostParaMedSetNoPage(Map<String,Object> entityMap) throws DataAccessException;
}
