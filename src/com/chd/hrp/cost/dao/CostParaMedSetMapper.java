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

import com.chd.hrp.cost.entity.CostParaMedSet;

/**
* @Title. @Description.
* 医技分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaMedSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 添加CostParaMedSet
	 * @param CostParaMedSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量添加CostParaMedSet
	 * @param  CostParaMedSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaMedSet>
	 * @throws DataAccessException
	*/
	public List<CostParaMedSet> queryCostParaMedSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSet所有数据
	 * @param  entityMap
	 * @return List<CostParaMedSet>
	 * @throws DataAccessException
	*/
	public List<CostParaMedSet> queryCostParaMedSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 查询CostParaMedSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaMedSet queryCostParaMedSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 删除CostParaMedSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量删除CostParaMedSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 医技分摊设置<BR> 更新CostParaMedSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaMedSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置<BR> 批量更新CostParaMedSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaMedSet(List<Map<String, Object>> entityMap)throws DataAccessException;
}
