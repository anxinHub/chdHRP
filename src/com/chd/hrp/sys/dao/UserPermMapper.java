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
import com.chd.hrp.sys.entity.UserPerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface UserPermMapper extends SqlMapper{
	
	/**
	 * @Description 添加UserPerm
	 * @param UserPerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加UserPerm
	 * @param  UserPerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询UserPerm分页
	 * @param  entityMap RowBounds
	 * @return List<UserPerm>
	 * @throws DataAccessException
	*/
	public List<UserPerm> queryUserPerm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询UserPerm所有数据
	 * @param  entityMap
	 * @return List<UserPerm>
	 * @throws DataAccessException
	*/
	public List<UserPerm> queryUserPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询UserPermByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public UserPerm queryUserPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除UserPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除UserPermByGroupId
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUserPermByGroupId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除UserPermByHosId
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUserPermByHosId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除UserPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新UserPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新UserPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询UserPerm上级编码
	 * @param  entityMap
	 * @return List<UserPerm>
	 * @throws DataAccessException
	*/
	public List<String> queryUserPermByLogin(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<String> queryUserPermByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>> queryHosCopyCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>> queryModPermByTree(Map<String,Object> entityMap) throws DataAccessException;
	
}
