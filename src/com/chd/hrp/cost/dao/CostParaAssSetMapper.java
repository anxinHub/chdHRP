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

import com.chd.hrp.cost.entity.CostParaAssSet;

/**
* @Title. @Description.
* 医辅分摊设置<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaAssSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 添加CostParaAssSet
	 * @param CostParaAssSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量添加CostParaAssSet
	 * @param  CostParaAssSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSet分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaAssSet>
	 * @throws DataAccessException
	*/
	public List<CostParaAssSet> queryCostParaAssSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSet所有数据
	 * @param  entityMap
	 * @return List<CostParaAssSet>
	 * @throws DataAccessException
	*/
	public List<CostParaAssSet> queryCostParaAssSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 查询CostParaAssSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaAssSet queryCostParaAssSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 删除CostParaAssSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量删除CostParaAssSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 更新CostParaAssSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaAssSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置<BR> 批量更新CostParaAssSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaAssSet(List<Map<String, Object>> entityMap)throws DataAccessException;
}
