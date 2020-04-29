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

import com.chd.hrp.sys.entity.RolePerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RolePermMapper extends SqlMapper{
	
	/**
	 * @Description 添加RolePerm
	 * @param RolePerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRolePerm(Map<String,String> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加RolePerm
	 * @param  RolePerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RolePerm分页
	 * @param  entityMap RowBounds
	 * @return List<RolePerm>
	 * @throws DataAccessException
	*/
	public List<RolePerm> queryRolePerm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询RolePerm所有数据
	 * @param  entityMap
	 * @return List<RolePerm>
	 * @throws DataAccessException
	*/
	public List<RolePerm> queryRolePerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RolePermByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public RolePerm queryRolePermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除RolePerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除RolePerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新RolePerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateRolePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新RolePerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchRolePerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询RolePerm上级编码
	 * @param  entityMap
	 * @return List<RolePerm>
	 * @throws DataAccessException
	*/
	public List<String> queryRolePermByMenu(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 查询RolePerm上级编码
	 * @param  entityMap
	 * @return List<RolePerm>
	 * @throws DataAccessException
	 */
	public List<String> queryRolePermByUserMenu(Map<String,Object> entityMap) throws DataAccessException;
}
