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
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.dao.SupTypeMapper;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.service.SupTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("supTypeService")
public class SupTypeServiceImpl implements SupTypeService {

	private static Logger logger = Logger.getLogger(SupTypeServiceImpl.class);
	
	@Resource(name = "supTypeMapper")
	private final SupTypeMapper supTypeMapper = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
    
	/**
	 * @Description 添加SupType
	 * @param SupType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addSupType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		SupType supType = querySupTypeByCode(entityMap);

		if (supType != null) {

			return "{\"error\":\"编码：" + supType.getType_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
		try {
			
			supTypeMapper.addSupType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSupType\"}";

		}

	}
	
	/**
	 * @Description 批量添加SupType
	 * @param  SupType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchSupType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			supTypeMapper.addBatchSupType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchSupType\"}";

		}
	}
	
	/**
	 * @Description 查询SupType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querySupType(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<SupType> list = supTypeMapper.querySupType(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<SupType> list = supTypeMapper.querySupType(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 查询SupTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public SupType querySupTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return supTypeMapper.querySupTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchSupType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList){
					List<Sup> list = supMapper.querySup(item);
					if(list.size() > 0){
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				
				int state = supTypeMapper.deleteBatchSupType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchSupType\"}";

		}
		
	}
	
		/**
	 * @Description 删除SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteSupType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				supTypeMapper.deleteSupType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteSupType\"}";

		}
    }
	
	/**
	 * @Description 更新SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateSupType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			supTypeMapper.updateSupType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSupType\"}";

		}
	}
	
	/**
	 * @Description 批量更新SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchSupType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			supTypeMapper.updateBatchSupType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSupType\"}";

		}
		
	}
	
	/**
	 * @Description 导入SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importSupType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
