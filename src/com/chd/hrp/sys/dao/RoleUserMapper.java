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

import com.chd.hrp.sys.entity.RoleUser;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RoleUserMapper extends SqlMapper{
	
	/**
	 * @Description 添加RoleUser
	 * @param RoleUser entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加RoleUser
	 * @param  RoleUser entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RoleUser分页
	 * @param  entityMap RowBounds
	 * @return List<RoleUser>
	 * @throws DataAccessException
	*/
	public List<RoleUser> queryRoleUser(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询RoleUser所有数据
	 * @param  entityMap
	 * @return List<RoleUser>
	 * @throws DataAccessException
	*/
	public List<RoleUser> queryRoleUser(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RoleUserByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public RoleUser queryRoleUserByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除RoleUser
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除RoleUser
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新RoleUser
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新RoleUser
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
}
