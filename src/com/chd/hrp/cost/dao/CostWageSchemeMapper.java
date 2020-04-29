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

import com.chd.hrp.cost.entity.CostWageScheme;

/**
* @Title. @Description.
* 职工工资查询方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageSchemeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 添加CostWageScheme
	 * @param CostWageScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量添加CostWageScheme
	 * @param  CostWageScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageScheme分页
	 * @param  entityMap RowBounds
	 * @return List<CostWageScheme>
	 * @throws DataAccessException
	*/
	public List<CostWageScheme> queryCostWageScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageScheme所有数据
	 * @param  entityMap
	 * @return List<CostWageScheme>
	 * @throws DataAccessException
	*/
	public List<CostWageScheme> queryCostWageScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageSchemeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageScheme queryCostWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 删除CostWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量删除CostWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 更新CostWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量更新CostWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询方案序列<BR> 查询queryCostWageSequence
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageScheme queryCostWageSequence()throws DataAccessException;
}
