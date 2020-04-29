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
import com.chd.hrp.sys.dao.ProjUseMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.ProjUse;
import com.chd.hrp.sys.service.ProjUseService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("projUseService")
public class ProjUseServiceImpl implements ProjUseService {

	private static Logger logger = Logger.getLogger(ProjUseServiceImpl.class);
	
	@Resource(name = "projUseMapper")
	private final ProjUseMapper projUseMapper = null;
    
	/**
	 * @Description 添加ProjUse
	 * @param ProjUse entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addProjUse(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		ProjUse projUse = queryProjUseByCode(entityMap);

		if (projUse != null) {

			return "{\"error\":\"编码：" + projUse.getUse_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("use_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("use_name").toString()));
		try {
			
			projUseMapper.addProjUse(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addProjUse\"}";

		}

	}
	
	/**
	 * @Description 批量添加ProjUse
	 * @param  ProjUse entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchProjUse(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			projUseMapper.addBatchProjUse(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchProjUse\"}";

		}
	}
	
	/**
	 * @Description 查询ProjUse分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryProjUse(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<ProjUse> list = projUseMapper.queryProjUse(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<ProjUse> list = projUseMapper.queryProjUse(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 查询ProjUseByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ProjUse queryProjUseByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return projUseMapper.queryProjUseByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchProjUse(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = projUseMapper.deleteBatchProjUse(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchProjUse\"}";

		}
		
	}
	
		/**
	 * @Description 删除ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteProjUse(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				projUseMapper.deleteProjUse(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteProjUse\"}";

		}
    }
	
	/**
	 * @Description 更新ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateProjUse(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("use_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("use_name").toString()));
			projUseMapper.updateProjUse(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjUse\"}";

		}
	}
	
	/**
	 * @Description 批量更新ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchProjUse(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			projUseMapper.updateBatchProjUse(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateProjUse\"}";

		}
		
	}
	
	/**
	 * @Description 导入ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importProjUse(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
