/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostWageDetailMap;

/**
* @Title. @Description.
* 工资明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageDetailMapService {

	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 添加CostWageDetailMap
	 * @param CostWageDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量添加CostWageDetailMap
	 * @param  CostWageDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMap分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostWageDetailMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMapByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostWageDetailMap queryCostWageDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 删除CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量删除CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 更新CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量更新CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostWageDetailMap querySequence()throws DataAccessException;
	
}
