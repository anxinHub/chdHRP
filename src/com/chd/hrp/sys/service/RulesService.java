/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Rules;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RulesService {

	/**
	 * @Description 添加Rules
	 * @param Rules entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Rules
	 * @param  Rules entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Rules分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRules(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RulesByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Rules queryRulesByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateRules(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchRules(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Rules
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importRules(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Map<String, Object>> queryRulesByProjCode() throws DataAccessException;
	
}
