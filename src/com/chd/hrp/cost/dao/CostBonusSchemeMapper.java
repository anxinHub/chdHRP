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
import com.chd.hrp.cost.entity.CostBonusScheme;
import com.chd.hrp.cost.entity.CostWageScheme;
import com.chd.hrp.cost.entity.CostWageSchemeSet;

/**
* @Title. @Description.
* 职工奖金查询方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusSchemeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 添加CostBonusScheme
	 * @param CostBonusScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量添加CostBonusScheme
	 * @param  CostBonusScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 查询CostBonusScheme分页
	 * @param  entityMap RowBounds
	 * @return List<CostBonusScheme>
	 * @throws DataAccessException
	*/
	public List<CostBonusScheme> queryCostBonusScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 查询CostBonusScheme所有数据
	 * @param  entityMap
	 * @return List<CostBonusScheme>
	 * @throws DataAccessException
	*/
	public List<CostBonusScheme> queryCostBonusScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 查询CostBonusSchemeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusScheme queryCostBonusSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;

	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 删除CostBonusScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	
	
	public int deleteCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量删除CostBonusScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 更新CostBonusScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostBonusScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工奖金查询方案<BR> 批量更新CostBonusScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostBonusScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询方案序列<BR> 查询queryCostBonusSequence
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusScheme queryCostBonusSequence()throws DataAccessException;
}
