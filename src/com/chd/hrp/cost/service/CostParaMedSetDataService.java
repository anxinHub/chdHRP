/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostParaMedSetData;

/**
* @Title. @Description.
* 医技分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaMedSetDataService {

	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 添加CostParaMedSetData
	 * @param CostParaMedSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量添加CostParaMedSetData
	 * @param  CostParaMedSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaMedSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaMedSetData queryCostParaMedSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 删除CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量删除CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 更新CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量更新CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaMedSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
