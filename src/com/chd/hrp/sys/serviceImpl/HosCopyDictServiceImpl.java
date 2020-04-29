/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.HosCopyDictMapper;
import com.chd.hrp.sys.service.HosCopyDictService;
import com.github.pagehelper.PageInfo;

/**
 * 单位账套维护
 * @author gaopei
 *
 */

@Service("hosCopyDictService")
public class HosCopyDictServiceImpl implements HosCopyDictService {

	private static Logger logger = Logger.getLogger(HosCopyDictServiceImpl.class);

	@Resource(name = "hosCopyDictMapper")
	private final HosCopyDictMapper hosCopyDictMapper = null;
 
	/**
	 * 添加   单位账套维护
	 */
	@Override
	public String addSysHosCopyDict(Map<String, Object> entityMap) throws DataAccessException {
		 
		entityMap.put("group_id", SessionManager.getGroupId()); 

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("rela_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("rela_name").toString()));
		
		Map<String,Object>  map = hosCopyDictMapper.queryHosCopyDictByCode(entityMap);

		if (map != null) {

			return "{\"error\":\"编码：" + map.get("rela_code") + "已存在.\"}";

		}
		try {

			hosCopyDictMapper.addSysHosCopyDict(entityMap); 

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCopy\"}";

		}

	}
	/**
	 * 查询   单位账套维护
	 */
	@Override
	public String querySysHosCopyDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<?> list = hosCopyDictMapper.querySysHosCopyDict(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}
	/**
	 * 批量删除   单位账套维护
	 */ 
	@Override
	public String deleteSysHosCopyDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try { 
			 hosCopyDictMapper.deleteBatchSysHosCopyRela(entityList);
			int state = hosCopyDictMapper.deleteBatchSysHosCopyDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCopy\"}";

		}

	}
 
	/**
	 * 修改   单位账套维护
	 */ 
	@Override
	public String updateSysHosCopyDict(Map<String, Object> entityMap) throws DataAccessException {
		
		System.out.println("aaaaaaaaa"+entityMap);

		entityMap.put("group_id", SessionManager.getGroupId()); 

		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("rela_name").toString()));
		
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("rela_name").toString()));
		 
		try {

			hosCopyDictMapper.updateSysHosCopyDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCopy\"}";

		}
	}
	@Override
	public Map<String,Object> queryHosCopyDictByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		 
		return  hosCopyDictMapper.queryHosCopyDictByCode(entityMap);
	}

	/**
	 * 查询   对应关系设置操作查询
	 */
	@Override
	public String querySysHosCopyRela(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<?> list = hosCopyDictMapper.querySysHosCopyRela(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}
	
	/**
	 * 添加   对应关系保存
	 */
	@Override
	public String addBatchSysHosCopyRela(List<Map<String, Object>> entityList) throws DataAccessException {
		  
		try {
			
			hosCopyDictMapper.addBatchSysHosCopyRela(entityList); 

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCopy\"}";

		}

	}
	@Override
	public String deleteBatchSysHosCopyRela(List<Map<String, Object>> entityList)
			throws DataAccessException { 
			try {
				
				hosCopyDictMapper.deleteBatchSysHosCopyRela(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStoreDetail\"}";

			}	
	}
 
}
