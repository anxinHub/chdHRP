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
import com.chd.hrp.sys.entity.Role;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RoleMapper extends SqlMapper{
	
	/**
	 * @Description 添加Role
	 * @param Role entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Role
	 * @param  Role entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Role分页
	 * @param  entityMap RowBounds
	 * @return List<Role>
	 * @throws DataAccessException
	*/
	public List<Role> queryRole(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Role所有数据
	 * @param  entityMap
	 * @return List<Role>
	 * @throws DataAccessException
	*/
	public List<Role> queryRole(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RoleByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Role queryRoleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Role
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Role
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Role
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Role
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryRoleUser(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	public List<Map<String, Object>> queryRoleUserByAdd(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;


	/**
	 * @Description 获取编码规则<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException;
}
