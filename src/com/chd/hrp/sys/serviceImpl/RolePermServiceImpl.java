/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.ViewToZtree;
import com.chd.base.util.ZtreeEntity;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.RolePermMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.UserPermMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.RolePerm;
import com.chd.hrp.sys.entity.SysMenu;
import com.chd.hrp.sys.service.RolePermService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("rolePermService")
public class RolePermServiceImpl implements RolePermService {

	private static Logger logger = Logger.getLogger(RolePermServiceImpl.class);

	@Resource(name = "rolePermMapper")
	private final RolePermMapper rolePermMapper = null;

	@Resource(name = "infoDictMapper")
	private final InfoDictMapper infoDictMapper = null;
	
	@Resource(name = "userPermMapper")
	private final UserPermMapper userPermMapper = null;

	/**
	 * @Description 添加RolePerm
	 * @param RolePerm
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addRolePerm(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			
			rolePermMapper.deleteRolePerm(entityMap);
			String listVo=entityMap.get("listVo").toString();
			// 添加用户权限
			if (listVo.length() > 0) {
				
				for (String s : entityMap.get("listVo").toString().split(",")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("mod_code", entityMap.get("mod_code"));
					map.put("role_id", entityMap.get("role_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("perm_code", s);
					list.add(map);
				}
				
				//修改成为每间隔1000条提交一次
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));

						if (i>0 && i % 1000 == 0) {
							rolePermMapper.addBatchRolePerm(batchList);
							batchList = new ArrayList<Map<String, Object>>();
						}

					}

					if(batchList.size() > 0){
						rolePermMapper.addBatchRolePerm(batchList);
					}

				}
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	/**
	 * @Description 批量添加RolePerm
	 * @param RolePerm
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchRolePerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			rolePermMapper.addBatchRolePerm(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRolePerm\"}";

		}
	}

	/**
	 * @Description 查询RolePerm分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryRolePerm(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<RolePerm> list = rolePermMapper.queryRolePerm(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 查询RolePermByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public RolePerm queryRolePermByCode(Map<String, Object> entityMap) throws DataAccessException {

		return rolePermMapper.queryRolePermByCode(entityMap);

	}

	/**
	 * @Description 批量删除RolePerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchRolePerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = rolePermMapper.deleteBatchRolePerm(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchRolePerm\"}";

		}

	}

	/**
	 * @Description 删除RolePerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteRolePerm(Map<String, Object> entityMap) throws DataAccessException {
		String group_id = SessionManager.getGroupId();
		entityMap.put("group_id", group_id);
		try {
			rolePermMapper.deleteRolePerm(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteRolePerm\"}";

		}
	}

	/**
	 * @Description 更新RolePerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateRolePerm(Map<String, Object> entityMap) throws DataAccessException {

		try {

			rolePermMapper.updateRolePerm(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRolePerm\"}";

		}
	}

	/**
	 * @Description 批量更新RolePerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchRolePerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			rolePermMapper.updateBatchRolePerm(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRolePerm\"}";

		}

	}

	/**
	 * @Description 导入RolePerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importRolePerm(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryRolePermByMenu(Map<String, Object> entityMap) throws DataAccessException {
		
		List<String> liPerm = rolePermMapper.queryRolePermByMenu(entityMap);
		
		String modCode=entityMap.get("mod_code").toString();
		List<SysMenu> rootList = LoadMenuFile.getMenuMap().get(modCode);
		String typeCode=SessionManager.getTypeCode();
		List<String> liUserPerm = null;
		if(typeCode.equals("0") || typeCode.equals("1")){
			//0医院用户、1集团用户
			//查询当前登录用户的角色权限视图，把拥有的权限授权给选择的用户
			entityMap.put("user_id", SessionManager.getUserId());
			liUserPerm = userPermMapper.queryUserPermByLogin(entityMap);
			if(liUserPerm==null || liUserPerm.size()==0){
				return "{Rows:[]}";
			}
		}
		
		
		StringBuilder json=new StringBuilder();
		json.append("{Rows:[");
		if(rootList!=null && rootList.size()>0){
			List<SysMenu> childList=null;
			for(SysMenu menu:rootList){
				
				//System.out.println("id："+menu.getId()+"，pid："+menu.getPid()+"，code："+menu.getCode()+"，name："+menu.getMenu_name()+"，url："+menu.getMenu_url()+"，permid："+menu.getPerm_id());
				String code=menu.getCode();
				if(modCode.equals("00") && code!=null && 
						(code.equalsIgnoreCase("sysManager") || code.equalsIgnoreCase("sysInfo") || code.equalsIgnoreCase("groupManager"))){
					//系统管理、系统字典、集团管理菜单不予授功能权限
					continue;
				}
				
				String permid=menu.getPerm_id();
				//检查当前登录用户的功能权限，把拥有的权限授权给选择的用户
				if(liUserPerm!=null && liUserPerm.size()>0){
					childList=new ArrayList<SysMenu>();
					if(permid!=null && !permid.equals("")){
						//末级
						if(!liUserPerm.contains(menu.getPerm_id())){
							continue;
						}
					}else if(MyConfig.checkMenuPerm(rootList,liUserPerm,menu.getId(),childList).size()==0){
						//非末级
						continue;
					}
				}
				
				json.append("{");
				json.append("\"id\":"+menu.getId());
				json.append(",\"pId\":"+menu.getPid());
				json.append(",\"name\":\""+menu.getMenu_name()+"\"");
				json.append(",\"permid\":\""+permid+"\"");
				json.append(",\"checked\":"+liPerm.contains(permid)+"");
				json.append("},");
				
			}
			json.setCharAt(json.length() - 1, ']'); //替换最后一个逗号
		}else{
			json.append("]");
		}
		json.append("}");
		return json.toString();
		
		
	/*	Map<String, Object> map = ViewToZtree.getTreeData(entityMap.get("mod_code").toString(), liGroupPerm);
		// 调用公用类 方法有说明
		ZtreeEntity root = new ZtreeEntity();
		ZtreeEntity data = new ZtreeEntity();
		root = (ZtreeEntity) map.get("root");
		data = (ZtreeEntity) map.get("data");
		List<String> userPerm = (List<String>) map.get("userPerm");
		return ViewToZtree.zTreeData(root, data, userPerm);*/
	}

	private Map<String, Object> queryHosInfoToGroupInfo(Map<String, Object> entityMap) throws DataAccessException {
		return infoDictMapper.queryHosInfoToGroupInfo(entityMap);
	}
}
