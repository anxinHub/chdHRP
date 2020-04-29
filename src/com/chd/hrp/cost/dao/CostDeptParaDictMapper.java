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

import com.chd.hrp.cost.entity.CostDeptParaDict;

/**
* @Title. @Description.
* 分摊参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptParaDictMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 分摊参数<BR> 添加CostDeptParaDict
	 * @param CostDeptParaDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量添加CostDeptParaDict
	 * @param  CostDeptParaDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDict分页
	 * @param  entityMap RowBounds
	 * @return List<CostDeptParaDict>
	 * @throws DataAccessException
	*/
	public List<CostDeptParaDict> queryCostDeptParaDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDict所有数据
	 * @param  entityMap
	 * @return List<CostDeptParaDict>
	 * @throws DataAccessException
	*/
	public List<CostDeptParaDict> queryCostDeptParaDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 查询CostDeptParaDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostDeptParaDict queryCostDeptParaDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 删除CostDeptParaDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量删除CostDeptParaDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 分摊参数<BR> 更新CostDeptParaDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostDeptParaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊参数<BR> 批量更新CostDeptParaDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostDeptParaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
}
