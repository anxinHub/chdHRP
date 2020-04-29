/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.RoleMapper;
import com.chd.hrp.sys.dao.RoleUserMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Role;
import com.chd.hrp.sys.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private static Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Resource(name = "roleMapper")
	private final RoleMapper roleMapper = null;
	
	@Resource(name = "roleUserMapper")
	private final RoleUserMapper roleUserMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 添加Role
	 * @param Role entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addRole(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("field_table", "sys_role");
		entityMap.put("field_key1", "role_name");
		String roleName=entityMap.get("role_name").toString();
		entityMap.put("field_value1", roleName);
		int count=sysFunUtilMapper.existsSysCodeNameByAdd(entityMap);
		
		if (count >0) {
			return "{\"error\":\"角色名称：" + roleName + "已存在.\"}";
		}
		entityMap.put("sj_id", SessionManager.getUserId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(roleName));
		entityMap.put("wbx_code", StringTool.toWuBi(roleName));
		
		try {
			
			roleMapper.addRole(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}

	}
	
	/**
	 * @Description 批量添加Role
	 * @param  Role entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchRole(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			roleMapper.addBatchRole(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRole\"}";

		}
	}
	
	/**
	 * @Description 查询Role分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRole(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Role> list = roleMapper.queryRole(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询RoleByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Role queryRoleByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return roleMapper.queryRoleByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchRole(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			roleUserMapper.deleteBatchRoleUser(entityList);
			roleMapper.deleteBatchRole(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
		
	}
	
		/**
	 * @Description 删除Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteRole(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				roleMapper.deleteRole(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteRole\"}";

		}
    }
	
	/**
	 * @Description 更新Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateRole(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("role_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("role_name").toString()));
		try {

			roleMapper.updateRole(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRole\"}";

		}
	}
	
	/**
	 * @Description 批量更新Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchRole(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			roleMapper.updateBatchRole(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRole\"}";

		}
		
	}
	
	/**
	 * @Description 导入Role
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importRole(Map<String,Object> entityMap)throws DataAccessException{

		try {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryRoleByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<Role> roleList = roleMapper.queryRole(entityMap);
		if (roleList.size()>0) {
			int row = 0;
			for (Role role : roleList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + role.getRole_id() + ",");
				jsonResult.append("group_id:'" + role.getGroup_id()+ "',");
				jsonResult.append("hos_id:'" + role.getHos_id()+ "',");
				jsonResult.append("name:'" + role.getRole_name()+ "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	//根据角色查询用户
	@Override
	public String queryRoleUser(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = roleMapper.queryRoleUser(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJsonLower(list, page.getTotal());
		
	}
	
	//角色添加用户，刨掉已经添加的用户
	@Override
	public String queryRoleUserByAdd(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = roleMapper.queryRoleUserByAdd(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJsonLower(list, page.getTotal());
		
	}
	
}
