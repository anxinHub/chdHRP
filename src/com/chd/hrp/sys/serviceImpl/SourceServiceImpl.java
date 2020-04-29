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
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.service.SourceService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("sourceService")
public class SourceServiceImpl implements SourceService {

	private static Logger logger = Logger.getLogger(SourceServiceImpl.class);
	
	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
    
	/**
	 * @Description 添加Source
	 * @param Source entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addSource(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		Source source = querySourceByCode(entityMap);

		if (source != null) {

			return "{\"error\":\"编码：" + source.getSource_code().toString() + "已存在.\"}";

		}
		Source sourcename = sourceMapper.querySourceByCodeByName(entityMap);
		if (sourcename!=null &&sourcename.getSource_name().equals(entityMap.get("source_name"))) {
			return "{\"error\":\"名称：" + sourcename.getSource_name().toString() + "已存在.\"}";
		}
		//查询source_id最大的值
		Long source_id = sourceMapper.querySourceByMaxId(entityMap);
		if(source_id == null || "".equals(source_id.toString())){
			entityMap.put("source_id", 1);
		}else{
			entityMap.put("source_id", source_id + 1);
		}
		
		//entityMap.put("source_attr", 0);
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("source_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("source_name").toString()));
		try {
			
			sourceMapper.addSource(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSource\"}";

		}

	}
	
	/**
	 * @Description 批量添加Source
	 * @param  Source entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchSource(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			sourceMapper.addBatchSource(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchSource\"}";

		}
	}
	
	/**
	 * @Description 查询Source分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querySource(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Source> list = sourceMapper.querySource(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询SourceByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Source querySourceByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return sourceMapper.querySourceByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchSource(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = sourceMapper.deleteBatchSource(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchSource\"}";

		}
		
	}
	
		/**
	 * @Description 删除Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteSource(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				sourceMapper.deleteSource(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteSource\"}";

		}
    }
	
	/**
	 * @Description 更新Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateSource(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("source_name").toString()));
			
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("source_name").toString()));
			
			sourceMapper.updateSource(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSource\"}";

		}
	}
	
	/**
	 * @Description 批量更新Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchSource(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			sourceMapper.updateBatchSource(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSource\"}";

		}
		
	}
	
	/**
	 * @Description 导入Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importSource(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String querySourceByAssPay(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Source> list = sourceMapper.querySourceByAssPay(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Source> list = sourceMapper.querySourceByAssPay(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> querySourcePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = sourceMapper.querySourcePrint(entityMap);
		
		return list;
	}
}
