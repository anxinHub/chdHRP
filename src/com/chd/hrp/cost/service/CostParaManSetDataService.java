/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostParaManSetData;

/**
* @Title. @Description.
* 管理分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaManSetDataService {

	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 添加CostParaManSetData
	 * @param CostParaManSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量添加CostParaManSetData
	 * @param  CostParaManSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaManSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaManSetData queryCostParaManSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 删除CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量删除CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 更新CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量更新CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaManSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
