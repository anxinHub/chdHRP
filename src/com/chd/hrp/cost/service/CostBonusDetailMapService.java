/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.entity.CostWageDetailMap;

/**
* @Title. @Description.
* 奖金明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusDetailMapService {

	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 添加CostBonusDetailMap
	 * @param CostBonusDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量添加CostBonusDetailMap
	 * @param  CostBonusDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMap分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostBonusDetailMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMapByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostBonusDetailMap queryCostBonusDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostBonusDetailMap querySequenceById()throws DataAccessException;
	
}
