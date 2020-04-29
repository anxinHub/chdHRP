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
import com.chd.hrp.sys.dao.HosTypeMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.HosType;
import com.chd.hrp.sys.service.HosTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("hosTypeService")
public class HosTypeServiceImpl implements HosTypeService {

	private static Logger logger = Logger.getLogger(HosTypeServiceImpl.class);
	
	@Resource(name = "hosTypeMapper")
	private final HosTypeMapper hosTypeMapper = null;
    
	/**
	 * @Description 添加HosType
	 * @param HosType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addHosType(Map<String,Object> entityMap)throws DataAccessException{
		
		HosType hosType = queryHosTypeByCode(entityMap);

		if (hosType != null) {

			return "{\"error\":\"编码：" + hosType.getType_code() + "已存在.\"}";

		}
		
		try {
			
			hosTypeMapper.addHosType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHosType\"}";

		}

	}
	
	/**
	 * @Description 批量添加HosType
	 * @param  HosType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchHosType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			hosTypeMapper.addBatchHosType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchHosType\"}";

		}
	}
	
	/**
	 * @Description 查询HosType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryHosType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<HosType> list = hosTypeMapper.queryHosType(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询HosTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public HosType queryHosTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return hosTypeMapper.queryHosTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchHosType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = hosTypeMapper.deleteBatchHosType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHosType\"}";

		}
		
	}
	
		/**
	 * @Description 删除HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteHosType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				hosTypeMapper.deleteHosType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHosType\"}";

		}
    }
	
	/**
	 * @Description 更新HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateHosType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			hosTypeMapper.updateHosType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHosType\"}";

		}
	}
	
	/**
	 * @Description 批量更新HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchHosType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			hosTypeMapper.updateBatchHosType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHosType\"}";

		}
		
	}
	
	/**
	 * @Description 导入HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importHosType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
