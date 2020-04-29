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
import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.ViewToZtree;
import com.chd.base.util.ZtreeEntity;
import com.chd.hrp.sys.dao.HrpSysSelectMapper;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.RolePermMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.UserPermMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.HrpSysSelect;
import com.chd.hrp.sys.entity.SysMenu;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.entity.UserPerm;
import com.chd.hrp.sys.service.LoginService;
import com.chd.hrp.sys.service.UserPermService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("userPermService")
public class UserPermServiceImpl implements UserPermService {

	private static Logger logger = Logger.getLogger(UserPermServiceImpl.class);

	@Resource(name = "userPermMapper")
	private final UserPermMapper userPermMapper = null;

	@Resource(name = "rolePermMapper")
	private final RolePermMapper rolePermMapper = null;

	@Resource(name = "userMapper")
	private final UserMapper userMapper = null;

	@Resource(name = "infoDictMapper")
	private final InfoDictMapper infoDictMapper = null;
	
	
	/**
	 * @Description 添加UserPerm
	 * @param UserPerm
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addUserPerm(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			
			userPermMapper.deleteUserPerm(entityMap);
			// 添加用户权限
			String listVo=entityMap.get("listVo").toString();
			if (listVo.length()> 0) {
				
				for (String s : listVo.split(",")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("mod_code", entityMap.get("mod_code"));
					map.put("user_id", entityMap.get("user_id"));
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

							userPermMapper.addBatchUserPerm(batchList);
							batchList = new ArrayList<Map<String, Object>>();
						}

					}

					if(batchList.size() > 0){
						
						userPermMapper.addBatchUserPerm(batchList);
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
	 * @Description 批量添加UserPerm
	 * @param UserPerm
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchUserPerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			userPermMapper.addBatchUserPerm(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchUserPerm\"}";

		}
	}

	/**
	 * @Description 查询UserPerm分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryUserPerm(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<UserPerm> list = userPermMapper.queryUserPerm(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());	

	}

	/**
	 * @Description 查询UserPermByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public UserPerm queryUserPermByCode(Map<String, Object> entityMap) throws DataAccessException {

		return userPermMapper.queryUserPermByCode(entityMap);

	}

	/**
	 * @Description 批量删除UserPerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchUserPerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = userPermMapper.deleteBatchUserPerm(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchUserPerm\"}";

		}

	}

	/**
	 * @Description 删除UserPerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteUserPerm(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> hos_info = queryHosInfoToGroupInfo(entityMap);
//		String group_id = hos_info.get("group_id").toString();
//		entityMap.put("group_id", group_id);
		List<Map<String, Object>> liHosCopy = userPermMapper.queryHosCopyCode(entityMap);
		try {
			for (int i = 0; i < liHosCopy.size(); i++) {
				entityMap.put("hos_id", liHosCopy.get(i).get("HOS_ID"));
				entityMap.put("copy_code", liHosCopy.get(i).get("COPY_CODE"));
				userPermMapper.deleteUserPerm(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteUserPerm\"}";
		}
	}

	/**
	 * @Description 更新UserPerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateUserPerm(Map<String, Object> entityMap) throws DataAccessException {

		try {

			userPermMapper.updateUserPerm(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateUserPerm\"}";

		}
	}

	/**
	 * @Description 批量更新UserPerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchUserPerm(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			userPermMapper.updateBatchUserPerm(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateUserPerm\"}";

		}

	}

	/**
	 * @Description 导入UserPerm
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importUserPerm(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryUserPermByMenu(Map<String, Object> entityMap) throws DataAccessException {
		
		List<String> liPerm = userPermMapper.queryUserPermByMenu(entityMap);
		
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
		
		/*Map<String, Object> map=new HashMap<String, Object>();
		User user = userMapper.queryUserByCode(entityMap);
		liPerm = userPermMapper.queryUserPermByMenu(entityMap);
		//liGroupPerm = rolePermMapper.queryRolePermByUserMenu(entityMap);
		map = ViewToZtree.getTreeData(entityMap.get("mod_code").toString(), user, liPerm, null);

		// 调用公用类 方法有说明
		ZtreeEntity root = new ZtreeEntity();
		ZtreeEntity data = new ZtreeEntity();
		root = (ZtreeEntity) map.get("root");
		data = (ZtreeEntity) map.get("data");
		List<String> userPerm = (List<String>) map.get("userPerm");
		return ViewToZtree.zTreeData(root, data, userPerm);*/

	}
	
	
	@Override
	public String queryModPermByTree(Map<String, Object> map) throws DataAccessException {

		List<Map<String, Object>> list = userPermMapper.queryModPermByTree(map);
		StringBuilder json=new StringBuilder();
		boolean isMod=false;
		json.append("{Rows:[");
		
		if(list!=null && list.size()>0){
			for (Map<String, Object> m:list) {
				if (LoadMenuFile.getModMap().get(m.get("mod_code")) != null) {
					isMod=true;
					json.append("{");
					json.append("\"id\":\""+m.get("mod_code")+"\",");
					json.append("\"pId\":0,");
					json.append("\"name\":\""+m.get("mod_name")+"\"");
					json.append("},");
				}
			}
		}
		
		if(isMod){
			json.setCharAt(json.length()-1, ']');
		}else{
			json.append("]");
		}
		json.append("}");
		return json.toString();
	}
	
	
}
