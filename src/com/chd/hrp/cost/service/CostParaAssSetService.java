/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostParaAssSet;
import com.chd.hrp.cost.entity.CostParaManSet;

/**
* @Title. @Description.
* 医辅分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaAssSetService {

	/**
	 * @Description 
	 * 医辅分摊设置<BR> 添加CostParaAssSet
	 * @param CostParaAssSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量添加CostParaAssSet
	 * @param  CostParaAssSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaAssSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaAssSet queryCostParaAssSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 删除CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量删除CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 更新CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量更新CostParaAssSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostParaAssSet> queryCostParaAssSetNoPage(Map<String,Object> entityMap) throws DataAccessException;
	
}
