/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.service;

import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.User;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface UserService {

	/**
	 * @Description 添加User
	 * @param User
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addUser(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加User
	 * @param User
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addBatchUser(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询User分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryUser(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryUserByRole(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询UserByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public User queryUserByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 删除User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteUser(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 删除User
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteUserByGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除User
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteUserByHos(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 批量删除User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteBatchUser(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateUser(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateBatchUser(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 导入User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String importUser(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 切换系统模块，或者取消该模块的权限，修改最后登录系统模块
	 * 
	 * @param entityMap
	 */
	public String updateUserMod(Map<String, Object> userMap) throws DataAccessException;
	
	/**
	 * 修改用户密码
	 */
	public String updateUserPassword(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 重置用户密码
	 */
	public String reUserPassword(Map<String, Object> mapVo) throws DataAccessException;

	String updateBatchUserPwd(String userId)
			throws DataAccessException;

	List<Map<String, Object>> queryUserPrint(Map<String, Object> entityMap)
			throws DataAccessException;

	String queryUserByAdmin(Map<String, Object> entityMap)
			throws DataAccessException;
	
}
