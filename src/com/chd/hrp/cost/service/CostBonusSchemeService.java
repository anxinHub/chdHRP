/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBonusScheme;
import com.chd.hrp.cost.entity.CostWageScheme;

/**
* @Title. @Description.
* 职工奖金查询方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusSchemeService {

	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 添加CostBonusScheme
	 * @param CostBonusScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量添加CostBonusScheme
	 * @param  CostBonusScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 查询CostBonusScheme分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostBonusScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 查询CostBonusSchemeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostBonusScheme queryCostBonusSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 删除CostBonusScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量删除CostBonusScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 更新CostBonusScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量更新CostBonusScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostBonusScheme CostBonusSequence()throws DataAccessException;
	
}
