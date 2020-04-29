/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostParaAssSetData;

/**
* @Title. @Description.
* 医辅分摊设置采集数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaAssSetDataService {

	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 添加CostParaAssSetData
	 * @param CostParaAssSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量添加CostParaAssSetData
	 * @param  CostParaAssSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostParaAssSetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostParaAssSetData queryCostParaAssSetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 删除CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量删除CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 更新CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量更新CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostParaAssSetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
