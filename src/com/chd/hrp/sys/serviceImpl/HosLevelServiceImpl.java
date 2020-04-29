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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.HosLevelMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.HosLevel;
import com.chd.hrp.sys.service.HosLevelService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("hosLevelService")
public class HosLevelServiceImpl implements HosLevelService {

	private static Logger logger = Logger.getLogger(HosLevelServiceImpl.class);
	
	@Resource(name = "hosLevelMapper")
	private final HosLevelMapper hosLevelMapper = null;
    
	/**
	 * @Description 添加HosLevel
	 * @param HosLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addHosLevel(Map<String,Object> entityMap)throws DataAccessException{
		
		HosLevel hosLevel = queryHosLevelByCode(entityMap);

		if (hosLevel != null) {

			return "{\"error\":\"编码：" + hosLevel.getLevel_code() + "已存在.\"}";

		}
		
		try {
			
			hosLevelMapper.addHosLevel(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHosLevel\"}";

		}

	}
	
	/**
	 * @Description 批量添加HosLevel
	 * @param  HosLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchHosLevel(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			hosLevelMapper.addBatchHosLevel(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchHosLevel\"}";

		}
	}
	
	/**
	 * @Description 查询HosLevel分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryHosLevel(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<HosLevel> list = hosLevelMapper.queryHosLevel(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询HosLevelByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public HosLevel queryHosLevelByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return hosLevelMapper.queryHosLevelByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchHosLevel(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = hosLevelMapper.deleteBatchHosLevel(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHosLevel\"}";

		}
		
	}
	
		/**
	 * @Description 删除HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteHosLevel(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				hosLevelMapper.deleteHosLevel(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHosLevel\"}";

		}
    }
	
	/**
	 * @Description 更新HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateHosLevel(Map<String,Object> entityMap)throws DataAccessException{

		try {

			hosLevelMapper.updateHosLevel(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHosLevel\"}";

		}
	}
	
	/**
	 * @Description 批量更新HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchHosLevel(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			hosLevelMapper.updateBatchHosLevel(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHosLevel\"}";

		}
		
	}
	
	/**
	 * @Description 导入HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importHosLevel(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
