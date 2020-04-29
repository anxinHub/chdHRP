/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.RoleUser;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RoleUserService {

	/**
	 * @Description 添加RoleUser
	 * @param RoleUser entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加RoleUser
	 * @param  RoleUser entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RoleUser分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRoleUser(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<RoleUser> queryUserByRole(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 查询RoleUserByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public RoleUser queryRoleUserByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchRoleUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importRoleUser(Map<String,Object> entityMap)throws DataAccessException;
	
}
