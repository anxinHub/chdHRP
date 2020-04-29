/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sys.dao.GroupDictMapper;
import com.chd.hrp.sys.dao.GroupMapper;
import com.chd.hrp.sys.dao.GroupPermMapper;
import com.chd.hrp.sys.dao.InitProcMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Group;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.service.GroupService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("groupService")
public class GroupServiceImpl implements GroupService {

	private static Logger logger = Logger.getLogger(GroupServiceImpl.class);
	
	@Resource(name = "groupMapper")
	private final GroupMapper groupMapper = null;
	
	@Resource(name = "groupDictMapper")
	private final GroupDictMapper groupDictMapper = null;
	
	@Resource(name = "groupPermMapper")
	private final GroupPermMapper groupPermMapper = null;
	
	@Resource(name = "userMapper")
	private final UserMapper userMapper = null;
	
	@Resource(name = "initProcMapper")
	private final InitProcMapper initProcMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 添加Group
	 * @param Group entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addGroup(Map<String,Object> entityMap)throws DataAccessException{
	
		
		Group group = queryGroupByCode(entityMap);

		if (group != null) {

			return "{\"error\":\"集团编码：" + group.getGroup_code() + "已存在.\"}";

		}
		String adminUserCode=entityMap.get("user_code").toString();
		User user = userMapper.queryUserByUserCode(entityMap);

		if (user != null) {

			return "{\"error\":\"管理员，用户编码：" + adminUserCode + "已被占用.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("group_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("group_name").toString()));
		try {
			String str_max = groupMapper.queryGroupSortMax(entityMap);
			String group_sort = (str_max==null?"1":str_max);
			entityMap.put("group_sort", group_sort);
			int result = groupMapper.addGroup(entityMap);
			if(result > 0){
				entityMap.put("group_id", groupMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("note", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("group_sort", group_sort);
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("group_name").toString()));
				entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("group_name").toString()));
				int dictResult = groupDictMapper.addGroupDict(entityMap);
				if(dictResult > 0){
					WisdomCloud wc = new WisdomCloud();
					entityMap.put("user_pwd", wc.encrypt(StringTool.getString(entityMap.get("user_pwd").toString())));
					entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("user_name").toString()));
					entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("user_name").toString()));
					entityMap.put("user_code", adminUserCode);
					userMapper.addUser(entityMap);
				}
				initProcMapper.saveInitGroupProc(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);

		}

	}
	
	/**
	 * @Description 批量添加Group
	 * @param  Group entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchGroup(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			groupMapper.addBatchGroup(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchGroup\"}";

		}
	}
	
	/**
	 * @Description 查询Group分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryGroup(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Group> list = groupMapper.queryGroup(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	
	/**
	 * @Description 查询GroupByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Group queryGroupByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return groupMapper.queryGroupByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchGroup(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = groupMapper.deleteBatchGroup(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchGroup\"}";

		}
		
	}
	
		/**
	 * @Description 删除Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteGroup(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("field_table", "sys_user");
			mapVo.put("field_id", "type_code");
			mapVo.put("field_id_value", "3");
			//判断当前集团是否不包含集团管理员3的用户
			int count=sysFunUtilMapper.existsSysCodeNameByUpdate(mapVo);
			if(count > 0){
				return "{\"error\":\"该集团已存在用户，不能删除.\"}";
			}
			groupDictMapper.deleteGroupDictByGroupId(entityMap);
			groupPermMapper.deleteGroupPermByGroupId(entityMap);
			groupMapper.deleteGroup(entityMap);
			userMapper.deleteUserByGroup(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
    }
	
	/**
	 * @Description 更新Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGroup(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			Map<String,Object> checkMap=new HashMap<String,Object>();
			checkMap.put("field_table", "sys_user");
			checkMap.put("field_key1", "user_code");
			String userCode=entityMap.get("user_code").toString();
			checkMap.put("field_value1", userCode);
			checkMap.put("field_id", "user_id");
			checkMap.put("field_id_value", entityMap.get("user_id"));
			
			int count=sysFunUtilMapper.existsSysCodeNameByUpdate(checkMap);
			if (count >0) {
				return "{\"error\":\"管理员，用户编码：" + userCode + "已被占用.\"}";
			}
			userMapper.updateUserCode(entityMap);
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("group_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("group_name").toString()));
			groupMapper.updateGroup(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);

		}
	}
	
	/**
	 * @Description 批量更新Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchGroup(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
				
			groupMapper.updateBatchGroup(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroup\"}";

		}
		
	}
	
	/**
	 * @Description 导入Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importGroup(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	/**
	 * @Description 查询role菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryGroupByMenu(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<Group> groupList = groupMapper.queryGroup(entityMap);
		
		if (groupList.size()>0) {
			
			int row = 0;
			
			for (Group group : groupList) {
				
				if (row == 0) {
					
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				
				row++;
				
				jsonResult.append("id:" + group.getGroup_id() + ",");
				
				jsonResult.append("name:'" + group.getGroup_simple()+ "',");
				
				jsonResult.append("}");
				
			}
			
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
		
	}



	@Override
	public String updateGroupCode(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("group_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("group_name").toString()));
			groupMapper.updateGroupCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroup\"}";

		}
	}

	@Override
	public String updateGroupName(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("group_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("group_name").toString()));
			entityMap.put("user_code", SessionManager.getUserCode());
			groupMapper.updateGroupName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			throw new SysException(e.getMessage(),e);

		}
	}
}
