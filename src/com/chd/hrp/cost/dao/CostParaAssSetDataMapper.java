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

import com.chd.hrp.cost.entity.CostParaAssSetData;

/**
* @Title. @Description.
* 医辅分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaAssSetDataMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 添加CostParaAssSetData
	 * @param CostParaAssSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量添加CostParaAssSetData
	 * @param  CostParaAssSetData entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetData分页
	 * @param  entityMap RowBounds
	 * @return List<CostParaAssSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaAssSetData> queryCostParaAssSetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaAssSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaAssSetData> queryCostParaAssSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetDataByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostParaAssSetData queryCostParaAssSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 删除CostParaAssSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量删除CostParaAssSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 更新CostParaAssSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量更新CostParaAssSetData
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetData所有数据
	 * @param  entityMap
	 * @return List<CostParaAssSetData>
	 * @throws DataAccessException
	*/
	public List<CostParaAssSetData> queryCostParaAssSetDataByShare(Map<String,Object> entityMap) throws DataAccessException;
}
