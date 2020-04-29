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
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.ProjTypeMapper;
import com.chd.hrp.sys.entity.ProjDict;
import com.chd.hrp.sys.entity.ProjType;
import com.chd.hrp.sys.service.ProjDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("projDictService")
public class ProjDictServiceImpl implements ProjDictService {

	private static Logger logger = Logger.getLogger(ProjDictServiceImpl.class);
	
	@Resource(name = "projDictMapper")
	private final ProjDictMapper projDictMapper = null;
	
	@Resource(name = "projTypeMapper")
	private final ProjTypeMapper projTypeMapper = null;
    
	/**
	 * @Description 添加ProjDict
	 * @param ProjDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addProjDict(Map<String,Object> entityMap)throws DataAccessException{
		
		ProjDict projDict = queryProjDictByCode(entityMap);

		if (projDict != null) {

			return "{\"error\":\"编码：" + projDict.getProj_code().toString() + "已存在.\"}";

		}
		
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
		    map.put("is_stop", 1);
		    map.put("group_id", entityMap.get("group_id"));
		    map.put("hos_id", entityMap.get("hos_id"));
		    map.put("proj_code", entityMap.get("proj_code"));
		    map.put("proj_id", entityMap.get("proj_id"));
		    projDictMapper.updateProjDictState(map);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			projDictMapper.addProjDict(entityMap);
			
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"变更失败 数据库异常 请联系管理员! 错误编码 addProjDict\"}";

		}

	}
	
	/**
	 * @Description 批量添加ProjDict
	 * @param  ProjDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchProjDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			projDictMapper.addBatchProjDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchProjDict\"}";

		}
	}
	
	/**
	 * @Description 查询ProjDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryProjDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<ProjDict> list = projDictMapper.queryProjDict(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public String queryProjDictList(Map<String, Object> paramMap) throws DataAccessException {
		if (paramMap.get("proj_name") != null) {
			paramMap.put("proj_name", paramMap.get("proj_name").toString().toUpperCase());
		}

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = projDictMapper.queryProjDictList(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = projDictMapper.queryProjDictList(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询ProjDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ProjDict queryProjDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return projDictMapper.queryProjDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchProjDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = projDictMapper.deleteBatchProjDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchProjDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteProjDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				projDictMapper.deleteProjDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteProjDict\"}";

		}
    }
	
	/**
	 * @Description 更新ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateProjDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			projDictMapper.updateProjDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchProjDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			projDictMapper.updateBatchProjDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importProjDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryProjDictBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<ProjDict> projList = projDictMapper.queryProjDictBySelector(entityMap);
		
		List<ProjType> typeList = projTypeMapper.queryProjType(entityMap);
		
		if (projList.size()>0) {
			
			for(ProjType projType : typeList){
				jsonResult.append("{id:'"+projType.getType_code()+"',pId:'0',name:'"+projType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (ProjDict subjDict : projList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + subjDict.getProj_id() + "',");
				
				jsonResult.append("pId:'"+subjDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + subjDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + subjDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+subjDict.getProj_code()+ " "+ subjDict.getProj_name() +"'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}

	@Override
	public List<Map<String, Object>> queryProjDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = projDictMapper.queryProjDictPrint(entityMap);
		
		return list;
		
	}
	
	/**
	 * 
	 * 用于 集团化项目选择器
	 */
	@Override
	public String queryGroupProjDictBySelector(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<ProjDict> projList = projDictMapper.queryProjDictBySelector(entityMap);
		
		List<ProjType> typeList = projTypeMapper.queryGroupProjType(entityMap);
		
		if (projList.size()>0) {
			
			for(ProjType projType : typeList){
				jsonResult.append("{id:'"+projType.getType_code()+"',pId:'0',name:'"+projType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (ProjDict subjDict : projList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + subjDict.getProj_id() + "',");
				
				jsonResult.append("pId:'"+subjDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + subjDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + subjDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+subjDict.getProj_code()+ " "+ subjDict.getProj_name() +"'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
}
