/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.RolePerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RolePermService {

	/**
	 * @Description 添加RolePerm
	 * @param RolePerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加RolePerm
	 * @param  RolePerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RolePerm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRolePerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RolePermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public RolePerm queryRolePermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除RolePerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除RolePerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新RolePerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新RolePerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入RolePerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RolePerm上级菜单
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRolePermByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
}
