/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Rules;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RulesMapper extends SqlMapper{
	
	/**
	 * @Description 添加Rules
	 * @param Rules entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Rules
	 * @param  Rules entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Rules分页
	 * @param  entityMap RowBounds
	 * @return List<Rules>
	 * @throws DataAccessException
	*/
	public List<Rules> queryRules(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Rules所有数据
	 * @param  entityMap
	 * @return List<Rules>
	 * @throws DataAccessException
	*/
	public List<Rules> queryRules(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RulesByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Rules queryRulesByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Rules
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Rules
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Rules
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Rules
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
}
