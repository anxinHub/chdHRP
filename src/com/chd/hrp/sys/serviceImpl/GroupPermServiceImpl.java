/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.GroupPermMapper;
import com.chd.hrp.sys.dao.PermMapper;
import com.chd.hrp.sys.dao.UserPermMapper;
import com.chd.hrp.sys.entity.GroupPerm;
import com.chd.hrp.sys.service.GroupPermService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("groupPermService")
public class GroupPermServiceImpl implements GroupPermService {

	private static Logger logger = Logger.getLogger(GroupPermServiceImpl.class);
	
	@Resource(name = "groupPermMapper")
	private final GroupPermMapper groupPermMapper = null;
	
	@Resource(name = "permMapper")
	private final PermMapper permMapper = null;
	
	@Resource(name = "userPermMapper")
	private final UserPermMapper userPermMapper = null;
    
	/**
	 * @Description 添加GroupPerm
	 * @param GroupPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addGroupPerm(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			groupPermMapper.addGroupPerm(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addGroupPerm\"}";

		}

	}
	
	/**
	 * @Description 批量添加GroupPerm
	 * @param  GroupPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchGroupPerm(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			groupPermMapper.addBatchGroupPerm(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchGroupPerm\"}";

		}
	}
	
	/**
	 * @Description 查询GroupPerm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryGroupPerm(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<GroupPerm> list = groupPermMapper.queryGroupPerm(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询GroupPerm
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAllGroupPerm(Map<String,Object> entityMap) throws DataAccessException{
		
		return JsonListBeanUtil.listToJson(groupPermMapper.queryGroupPerm(entityMap));
		
	}
	
	/**
	 * @Description 查询GroupPermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public GroupPerm queryGroupPermByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return groupPermMapper.queryGroupPermByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchGroupPerm(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = groupPermMapper.deleteBatchGroupPerm(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchGroupPerm\"}";

		}
		
	}
	
		/**
	 * @Description 删除GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteGroupPerm(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				groupPermMapper.deleteGroupPerm(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteGroupPerm\"}";

		}
    }
	
	/**
	 * @Description 更新GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGroupPerm(Map<String,Object> entityMap)throws DataAccessException{

		try {

			groupPermMapper.updateGroupPerm(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroupPerm\"}";

		}
	}
	
	/**
	 * @Description 批量更新GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchGroupPerm(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			groupPermMapper.updateBatchGroupPerm(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateGroupPerm\"}";

		}
		
	}
	
	/**
	 * @Description 导入GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importGroupPerm(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 根据GroupId删除GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	@Override
	public String deleteGroupPermByGroupId(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			//userPermMapper.deleteUserPermByGroupId(entityMap);//登陆在v_user_perm判断系统权限
			permMapper.deletePermByGroupId(entityMap);
			groupPermMapper.deleteGroupPermByGroupId(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	catch (Exception e) {

		logger.error(e.getMessage(), e);

		return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteGroupPerm\"}";

	}
	}
}
