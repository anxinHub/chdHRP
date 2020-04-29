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
import com.chd.hrp.sys.dao.StoreMapper;
import com.chd.hrp.sys.dao.StoreTypeMapper;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.entity.StoreType;
import com.chd.hrp.sys.service.StoreTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("storeTypeService")
public class StoreTypeServiceImpl implements StoreTypeService {

	private static Logger logger = Logger.getLogger(StoreTypeServiceImpl.class);
	
	@Resource(name = "storeTypeMapper")
	private final StoreTypeMapper storeTypeMapper = null;
	
	@Resource(name = "storeMapper")
	private final StoreMapper storeMapper = null;
    
	/**
	 * @Description 添加StoreType
	 * @param StoreType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addStoreType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		StoreType storeType = queryStoreTypeByCode(entityMap);

		if (storeType != null) {

			return "{\"error\":\"编码：" + storeType.getType_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
		try {
			
			storeTypeMapper.addStoreType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addStoreType\"}";

		}

	}
	
	/**
	 * @Description 批量添加StoreType
	 * @param  StoreType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchStoreType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			storeTypeMapper.addBatchStoreType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchStoreType\"}";

		}
	}
	
	/**
	 * @Description 查询StoreType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryStoreType(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<StoreType> list = storeTypeMapper.queryStoreType(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询StoreTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public StoreType queryStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return storeTypeMapper.queryStoreTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchStoreType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList){
					List<Store> list = storeMapper.queryStore(item);
					if(list.size() >0 ){
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				int state = storeTypeMapper.deleteBatchStoreType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchStoreType\"}";

		}
		
	}
	
		/**
	 * @Description 删除StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteStoreType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				storeTypeMapper.deleteStoreType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteStoreType\"}";

		}
    }
	
	/**
	 * @Description 更新StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateStoreType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			storeTypeMapper.updateStoreType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStoreType\"}";

		}
	}
	
	/**
	 * @Description 批量更新StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			storeTypeMapper.updateBatchStoreType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStoreType\"}";

		}
		
	}
	
	/**
	 * @Description 导入StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importStoreType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
