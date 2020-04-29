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

import com.chd.hrp.cost.entity.CostParaManSet;

/**
* @Title. @Description.
* 管理分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaManSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 添加CostParaManSet
	 * @param CostParaManSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量添加CostParaManSet
	 * @param  CostParaManSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaManSet>
	 * @throws DataAccessException
	*/
	public List<CostParaManSet> queryCostParaManSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSet所有数据
	 * @param  entityMap
	 * @return List<CostParaManSet>
	 * @throws DataAccessException
	*/
	public List<CostParaManSet> queryCostParaManSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 查询CostParaManSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaManSet queryCostParaManSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 删除CostParaManSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量删除CostParaManSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 管理分摊设置<BR> 更新CostParaManSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaManSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置<BR> 批量更新CostParaManSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaManSet(List<Map<String, Object>> entityMap)throws DataAccessException;
}
