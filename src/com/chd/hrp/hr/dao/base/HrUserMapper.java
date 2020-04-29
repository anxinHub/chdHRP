/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrUser;



/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HrUserMapper extends SqlMapper{
	
	/**
	 * @Description 添加User
	 * @param User entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加User
	 * @param  User entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询User分页
	 * @param  entityMap RowBounds
	 * @return List<User>
	 * @throws DataAccessException
	*/
	public List<HrUser> queryUser(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询User所有数据
	 * @param  entityMap
	 * @return List<User>
	 * @throws DataAccessException
	*/
	public List<HrUser> queryUser(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<HrUser> queryUserByRole(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 查询UserByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrUser queryUserByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询UserByUserCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrUser queryUserByUserCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUserByGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUserByHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchUser(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateUser(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新UserPass
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateUserPassByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新User
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchUser(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 切换系统模块，或者取消该模块的权限，修改最后登录系统模块
	 */
	public int updateUserMod(Map<String,Object> userMap)throws DataAccessException;
	
	/**
	 * 修改用户密码
	 */
	public int updateUserPassword(Map<String,Object> userMap)throws DataAccessException;	
	/**
	 * 修改集团和医院管理员用户编码
	 */
	public int updateUserCode(Map<String,Object> userMap)throws DataAccessException;	
	
	/**
	 * @Description 系统登录，根据用户编码查找用户所需要的信息
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrUser queryUserHosGroupByCode(Map<String,Object> entityMap)throws DataAccessException;
}
