/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.RoleUserMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.RoleUser;
import com.chd.hrp.sys.service.RoleUserService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("roleUserService")
public class RoleUserServiceImpl implements RoleUserService {

	private static Logger logger = Logger.getLogger(RoleUserServiceImpl.class);
	
	@Resource(name = "roleUserMapper")
	private final RoleUserMapper roleUserMapper = null;
    
	/**
	 * @Description 添加RoleUser
	 * @param RoleUser entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addRoleUser(Map<String,Object> entityMap)throws DataAccessException{
		
		RoleUser roleUser = queryRoleUserByCode(entityMap);

		if (roleUser != null) {

			return "{\"error\":\"编码：" + roleUser.getRole_id().toString() + "已存在.\"}";

		}
		
		try {
			roleUserMapper.deleteRoleUser(entityMap);
			roleUserMapper.addRoleUser(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addRoleUser\"}";

		}

	}
	
	/**
	 * @Description 批量添加RoleUser
	 * @param  RoleUser entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchRoleUser(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			roleUserMapper.deleteBatchRoleUser(entityList);
			roleUserMapper.addBatchRoleUser(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRoleUser\"}";

		}
	}
	
	/**
	 * @Description 查询RoleUser分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRoleUser(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<RoleUser> list = roleUserMapper.queryRoleUser(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public List<RoleUser> queryUserByRole(Map<String,Object> entityMap) throws DataAccessException{
		
		return roleUserMapper.queryRoleUser(entityMap);
		
	}
	
	/**
	 * @Description 查询RoleUserByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public RoleUser queryRoleUserByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return roleUserMapper.queryRoleUserByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchRoleUser(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = roleUserMapper.deleteBatchRoleUser(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchRoleUser\"}";

		}
		
	}
	
		/**
	 * @Description 删除RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteRoleUser(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				roleUserMapper.deleteRoleUser(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteRoleUser\"}";

		}
    }
	
	/**
	 * @Description 更新RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateRoleUser(Map<String,Object> entityMap)throws DataAccessException{

		try {

			roleUserMapper.updateRoleUser(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRoleUser\"}";

		}
	}
	
	/**
	 * @Description 批量更新RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchRoleUser(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			roleUserMapper.updateBatchRoleUser(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRoleUser\"}";

		}
		
	}
	
	/**
	 * @Description 导入RoleUser
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importRoleUser(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
