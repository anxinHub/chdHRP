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
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.ProjMapper;
import com.chd.hrp.sys.dao.ProjTypeMapper;
import com.chd.hrp.sys.entity.Proj;
import com.chd.hrp.sys.entity.ProjType;
import com.chd.hrp.sys.service.ProjTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("projTypeService")
public class ProjTypeServiceImpl implements ProjTypeService {

	private static Logger logger = Logger.getLogger(ProjTypeServiceImpl.class);
	
	@Resource(name = "projTypeMapper")
	private final ProjTypeMapper projTypeMapper = null;
	
	@Resource(name = "projMapper")
	private final ProjMapper projMapper = null;
	/**
	 * @Description 添加ProjType
	 * @param ProjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addProjType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		ProjType projType = queryProjTypeByCode(entityMap);

		if (projType != null) {

			return "{\"error\":\"编码：" + projType.getType_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
		try {
			
			projTypeMapper.addProjType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addProjType\"}";

		}

	}
	
	/**
	 * @Description 批量添加ProjType
	 * @param  ProjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchProjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			projTypeMapper.addBatchProjType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchProjType\"}";

		}
	}
	
	/**
	 * @Description 查询ProjType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryProjType(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<ProjType> list = projTypeMapper.queryProjType(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<ProjType> list = projTypeMapper.queryProjType(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 查询ProjTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ProjType queryProjTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return projTypeMapper.queryProjTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchProjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList){
					List<Proj> list = projMapper.queryProj(item);
					if(list.size() > 0){
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				int state = projTypeMapper.deleteBatchProjType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchProjType\"}";

		}
		
	}
	
		/**
	 * @Description 删除ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteProjType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				projTypeMapper.deleteProjType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteProjType\"}";

		}
    }
	
	/**
	 * @Description 更新ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateProjType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			projTypeMapper.updateProjType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjType\"}";

		}
	}
	
	/**
	 * @Description 批量更新ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchProjType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			projTypeMapper.updateBatchProjType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjType\"}";

		}
		
	}
	
	/**
	 * @Description 导入ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importProjType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
