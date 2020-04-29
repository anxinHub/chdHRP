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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.GroupDictMapper;
import com.chd.hrp.sys.dao.GroupMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.GroupDict;
import com.chd.hrp.sys.service.GroupDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("groupDictService")
public class GroupDictServiceImpl implements GroupDictService {

	private static Logger logger = Logger.getLogger(GroupDictServiceImpl.class);
	
	@Resource(name = "groupDictMapper")
	private final GroupDictMapper groupDictMapper = null;
	
	@Resource(name = "groupMapper")
	private final GroupMapper groupMapper = null;
	
	/**
	 * @Description 添加GroupDict
	 * @param GroupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addGroupDict(Map<String,Object> entityMap)throws DataAccessException{
		
		GroupDict groupDict = queryGroupDictByCode(entityMap);

		if (groupDict != null) {

			return "{\"error\":\"编码：" + groupDict.getGroup_code() + "已存在.\"}";

		}
		
		try {
		    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("is_stop", 1);
		    map.put("group_id", entityMap.get("group_id"));
			groupDictMapper.updateGroupDictState(map);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			String str_max = groupMapper.queryGroupSortMax(entityMap);
			String group_sort = (str_max==null?"1":str_max);
			entityMap.put("group_sort", group_sort); 
			groupDictMapper.addGroupDict(entityMap);
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("变更失败");

		}

	}
	
	/**
	 * @Description 批量添加GroupDict
	 * @param  GroupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchGroupDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			groupDictMapper.addBatchGroupDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchGroupDict\"}";

		}
	}
	
	/**
	 * @Description 查询GroupDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryGroupDict(Map<String,Object> entityMap) throws DataAccessException{
		
		List<GroupDict> list = groupDictMapper.queryGroupDict(entityMap);
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 查询GroupDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public GroupDict queryGroupDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return groupDictMapper.queryGroupDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchGroupDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = groupDictMapper.deleteBatchGroupDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchGroupDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteGroupDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				groupDictMapper.deleteGroupDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteGroupDict\"}";

		}
    }
	
	/**
	 * @Description 更新GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGroupDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			groupDictMapper.updateGroupDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroupDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchGroupDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			groupDictMapper.updateBatchGroupDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroupDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importGroupDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	/**
	 * @Description 查询GroupDict菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryGroupDictByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<GroupDict> groupDictList = groupDictMapper.queryGroupDict(entityMap);
		if (groupDictList.size()>0) {
			int row = 0;
			for (GroupDict groupDict : groupDictList) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + groupDict.getGroup_id() + ",");
				//jsonResult.append("pId:" + menu.getPid() + ",");
				jsonResult.append("name:'" + groupDict.getGroup_simple()+ "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
}
