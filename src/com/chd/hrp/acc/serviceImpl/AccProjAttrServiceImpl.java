/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import oracle.net.aso.e;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccProjAttrMapper;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.service.AccProjAttrService;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 项目字典属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accProjAttrService")
public class AccProjAttrServiceImpl implements AccProjAttrService {

	private static Logger logger = Logger.getLogger(AccProjAttrServiceImpl.class);
	
	@Resource(name = "accProjAttrMapper")
	private final AccProjAttrMapper accProjAttrMapper = null;
    
	@Resource(name = "projDictMapper")
	private final ProjDictMapper projDictMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 添加AccProjAttr
	 * @param AccProjAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccProjAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		AccProjAttr accProjAttr = queryAccProjAttrByCode(entityMap);

		if (accProjAttr != null) {

			accProjAttrMapper.updateAccProjAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}
		
		try {
			
			accProjAttrMapper.addAccProjAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccProjAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量添加AccProjAttr
	 * @param  AccProjAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccProjAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accProjAttrMapper.addBatchAccProjAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccProjAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccProjAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccProjAttr> list = accProjAttrMapper.queryAccProjAttr(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list,page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccProjAttr queryAccProjAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accProjAttrMapper.queryAccProjAttrByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量删除AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccProjAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accProjAttrMapper.deleteBatchAccProjAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccProjAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 删除AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccProjAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accProjAttrMapper.deleteAccProjAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccProjAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 更新AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccProjAttr(Map<String,Object> entityMap)throws DataAccessException{
		try{
			if(entityMap.get("app_date")  != null && !"".equals(entityMap.get("app_date").toString()) ){
				entityMap.put("app_date", DateUtil.stringToDate(entityMap.get("app_date").toString(),"yyyy-MM-dd"));
			}
			AccProjAttr accProjAttr = queryAccProjAttrByCode(entityMap);
			if (accProjAttr != null) {
	
				accProjAttrMapper.updateAccProjAttr(entityMap);
	
				//把当前数据修改成历史数据(保留历史数据)
				int updateCount = accProjAttrMapper.updateProDictHistory(entityMap);
				if (updateCount == 0) {
					throw new SysException("修改失败,请重新刷新页面后重试!");
				}
				
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
				entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "修改");
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("is_stop", 0);
				Map<String, Object> utilMap = new HashMap<String, Object>();
				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("hos_id", entityMap.get("hos_id"));
				utilMap.put("copy_code", "");
				utilMap.put("field_table", "HOS_PROJ");
				utilMap.put("field_key1", "");
				utilMap.put("field_value1", "");
				utilMap.put("field_key2", "");
				utilMap.put("field_value2", "");

				if (entityMap.get("sort_code").equals("系统生成")) {
					utilMap.remove("field_key2");
					utilMap.put("field_sort", "sort_code");
					int count = sysFunUtilMapper.querySysMaxSort(utilMap);
					entityMap.put("sort_code", count);
				}
				//添加修改后的数据
				int addCount = projDictMapper.addProjDict(entityMap);
				if (addCount == 0) {
					throw new SysException("修改失败,请重新刷新页面后重试!");
				}
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			}else{
				
				accProjAttrMapper.addAccProjAttr(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
			}
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccProjAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量更新AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccProjAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accProjAttrMapper.updateBatchAccProjAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccProjAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 导入AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccProjAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public AccProjAttr queryProjByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accProjAttrMapper.queryProjByCode(entityMap);
	}

	@Override
	public AccProjAttr queryHosProjByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accProjAttrMapper.queryHosProjByCode(entityMap);
	}

	@Override
	public AccProjAttr queryAccProjAttrByProj(Map<String, Object> entityMap)
			throws DataAccessException {
		return accProjAttrMapper.queryAccProjAttrByProj(entityMap);
	}
}
