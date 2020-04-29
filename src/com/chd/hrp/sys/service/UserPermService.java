/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.UserPerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface UserPermService {

	/**
	 * @Description 添加UserPerm
	 * @param UserPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加UserPerm
	 * @param  UserPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询UserPerm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryUserPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询UserPermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public UserPerm queryUserPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除UserPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除UserPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新UserPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新UserPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchUserPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入UserPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importUserPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询UserPerm菜单
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryUserPermByMenu(Map<String,Object> entityMap) throws DataAccessException;

	String queryModPermByTree(Map<String, Object> map)
			throws DataAccessException;
	
}
