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

import com.chd.hrp.cost.entity.CostParaManSetData;

/**
* @Title. @Description.
* 管理分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaManSetDataMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 添加CostParaManSetData
	 * @param CostParaManSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量添加CostParaManSetData
	 * @param  CostParaManSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetData分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaManSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaManSetData> queryCostParaManSetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaManSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaManSetData> queryCostParaManSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetDataByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaManSetData queryCostParaManSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 删除CostParaManSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量删除CostParaManSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 更新CostParaManSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量更新CostParaManSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaManSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaManSetData> queryCostParaManSetDataByShare(Map<String,Object> entityMap) throws DataAccessException;
}
