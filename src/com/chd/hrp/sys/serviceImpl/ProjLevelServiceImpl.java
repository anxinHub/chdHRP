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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.ProjLevelMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.ProjLevel;
import com.chd.hrp.sys.service.ProjLevelService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("projLevelService")
public class ProjLevelServiceImpl implements ProjLevelService {

	private static Logger logger = Logger.getLogger(ProjLevelServiceImpl.class);
	
	@Resource(name = "projLevelMapper")
	private final ProjLevelMapper projLevelMapper = null;
    
	/**
	 * @Description 添加ProjLevel
	 * @param ProjLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addProjLevel(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("level_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("level_name").toString()));
		ProjLevel projLevel = queryProjLevelByCode(entityMap);

		if (projLevel != null) {

			return "{\"error\":\"编码：" + projLevel.getLevel_code().toString() + "已存在.\"}";

		}
		
		try {
			
			projLevelMapper.addProjLevel(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addProjLevel\"}";

		}

	}
	
	/**
	 * @Description 批量添加ProjLevel
	 * @param  ProjLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchProjLevel(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			projLevelMapper.addBatchProjLevel(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchProjLevel\"}";

		}
	}
	
	/**
	 * @Description 查询ProjLevel分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryProjLevel(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<ProjLevel> list = projLevelMapper.queryProjLevel(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<ProjLevel> list = projLevelMapper.queryProjLevel(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 查询ProjLevelByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ProjLevel queryProjLevelByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return projLevelMapper.queryProjLevelByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchProjLevel(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = projLevelMapper.deleteBatchProjLevel(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchProjLevel\"}";

		}
		
	}
	
		/**
	 * @Description 删除ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteProjLevel(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				projLevelMapper.deleteProjLevel(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteProjLevel\"}";

		}
    }
	
	/**
	 * @Description 更新ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateProjLevel(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("level_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("level_name").toString()));
			projLevelMapper.updateProjLevel(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjLevel\"}";

		}
	}
	
	/**
	 * @Description 批量更新ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchProjLevel(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			projLevelMapper.updateBatchProjLevel(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjLevel\"}";

		}
		
	}
	
	/**
	 * @Description 导入ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importProjLevel(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
