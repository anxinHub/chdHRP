/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostParaManSet;

/**
* @Title. @Description.
* 管理分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaManSetService {

	/**
	 * @Description 
	 * 管理分摊设置<BR> 添加CostParaManSet
	 * @param CostParaManSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量添加CostParaManSet
	 * @param  CostParaManSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaManSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<CostParaManSet> queryCostParaManSetNoPage(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaManSet queryCostParaManSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 删除CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量删除CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 更新CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量更新CostParaManSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
