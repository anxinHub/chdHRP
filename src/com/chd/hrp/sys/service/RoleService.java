/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Role;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RoleService {

	/**
	 * @Description 添加Role
	 * @param Role entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Role
	 * @param  Role entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Role分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRole(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询Role菜单
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRoleByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RoleByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Role queryRoleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateRole(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchRole(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importRole(Map<String,Object> entityMap)throws DataAccessException;

	String queryRoleUser(Map<String, Object> entityMap)
			throws DataAccessException;

	String queryRoleUserByAdd(Map<String, Object> entityMap)
			throws DataAccessException;
	
}
