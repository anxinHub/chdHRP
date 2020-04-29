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

import com.chd.hrp.cost.entity.CostParaMedSetData;

/**
* @Title. @Description.
* 医技分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaMedSetDataMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 添加CostParaMedSetData
	 * @param CostParaMedSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量添加CostParaMedSetData
	 * @param  CostParaMedSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetData分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaMedSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaMedSetData> queryCostParaMedSetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaMedSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaMedSetData> queryCostParaMedSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetDataByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaMedSetData queryCostParaMedSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 删除CostParaMedSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量删除CostParaMedSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 更新CostParaMedSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量更新CostParaMedSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaMedSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaMedSetData> queryCostParaMedSetDataByShare(Map<String,Object> entityMap) throws DataAccessException;
}
