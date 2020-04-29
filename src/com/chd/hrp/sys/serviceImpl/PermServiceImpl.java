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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.PermMapper;
import com.chd.hrp.sys.dao.UserPermMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Info;
import com.chd.hrp.sys.entity.InfoDict;
import com.chd.hrp.sys.entity.Perm;
import com.chd.hrp.sys.service.PermService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("permService")
public class PermServiceImpl implements PermService {

	private static Logger logger = Logger.getLogger(PermServiceImpl.class);
	
	@Resource(name = "permMapper")
	private final PermMapper permMapper = null;
	
	@Resource(name = "userPermMapper")
	private final UserPermMapper userPermMapper = null;
	
	/**
	 * @Description 添加Perm
	 * @param Perm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPerm(Map<String,Object> entityMap)throws DataAccessException{
		
		Perm perm = queryPermByCode(entityMap);

		if (perm != null) {

			return "{\"error\":\"编码：" + perm.getMod_code().toString() + "已存在.\"}";

		}
		
		try {
			
			permMapper.addPerm(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addPerm\"}";

		}

	}
	
	/**
	 * @Description 批量添加Perm
	 * @param  Perm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPerm(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			permMapper.addBatchPerm(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchPerm\"}";

		}
	}
	
	/**
	 * @Description 查询Perm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPerm(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Perm> list =permMapper.queryPerm(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询PermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Perm queryPermByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return permMapper.queryPermByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPerm(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = permMapper.deleteBatchPerm(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchPerm\"}";

		}
		
	}
	
		/**
	 * @Description 删除Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deletePerm(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			//	userPermMapper.deleteUserPermByGroupId(entityMap);//登陆在v_user_perm判断系统权限
				permMapper.deletePerm(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deletePerm\"}";

		}
    }
	
	/**
	 * @Description 更新Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePerm(Map<String,Object> entityMap)throws DataAccessException{

		try {

			permMapper.updatePerm(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePerm\"}";

		}
	}
	
	/**
	 * @Description 批量更新Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPerm(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			permMapper.updateBatchPerm(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePerm\"}";

		}
		
	}
	
	/**
	 * @Description 导入Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importPerm(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	/**
	 * @Description 查询Perm所有数据
	 * @param  entityMap
	 * @return List<Perm>
	 * @throws DataAccessException
	*/
	@Override
	public String queryAllPerm(Map<String, Object> entityMap)
			throws DataAccessException {
		return JsonListBeanUtil.listToJson(permMapper.queryPerm(entityMap));
	}
	
}
