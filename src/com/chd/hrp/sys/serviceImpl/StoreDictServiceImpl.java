/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
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
import com.chd.hrp.sys.dao.StoreDictMapper;
import com.chd.hrp.sys.dao.StoreMapper;
import com.chd.hrp.sys.dao.StoreTypeMapper;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.entity.StoreType;
import com.chd.hrp.sys.service.StoreDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("storeDictService")
public class StoreDictServiceImpl implements StoreDictService {

	private static Logger logger = Logger.getLogger(StoreDictServiceImpl.class);
	
	@Resource(name = "storeDictMapper")
	private final StoreDictMapper storeDictMapper = null;
	
	@Resource(name = "storeMapper")
	private final StoreMapper storeMapper = null;
	
	@Resource(name = "storeTypeMapper")
	private final StoreTypeMapper storeTypeMapper = null;
    
	/**
	 * @Description 添加StoreDict
	 * @param StoreDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addStoreDict(Map<String,Object> entityMap)throws DataAccessException{
		
		StoreDict storeDict = queryStoreDictByCode(entityMap);

		if (storeDict != null) {

			return "{\"error\":\"编码：" + storeDict.getStore_code().toString() + "已存在.\"}";

		}
		
		try {
			
			if("0".endsWith(entityMap.get("dict_type").toString())){
				 
				storeMapper.updateStoreByCode(entityMap);
				 
			 }else if("1".endsWith(entityMap.get("dict_type").toString())){
				 
				 storeMapper.updateStoreByName(entityMap);
				 
			 }
			
			Map<String,Object> map = new HashMap<String,Object>();
			
		    map.put("is_stop", 1);
		    
		    map.put("group_id", entityMap.get("group_id"));
		    
		    map.put("hos_id", entityMap.get("hos_id"));
		    
		    map.put("store_code", entityMap.get("store_code"));
		    
		    map.put("store_id", entityMap.get("store_id"));
		    
		    storeDictMapper.updateStoreDictState(map);
		    
			entityMap.put("user_code", SessionManager.getUserCode());
			
			entityMap.put("create_date", new Date());
			
			storeDictMapper.addStoreDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addStoreDict\"}";

		}

	}
	
	/**
	 * @Description 批量添加StoreDict
	 * @param  StoreDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchStoreDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			storeDictMapper.addBatchStoreDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchStoreDict\"}";

		}
	}
	
	/**
	 * @Description 查询StoreDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryStoreDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<StoreDict> list = storeDictMapper.queryStoreDict(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public String queryStoreDictList(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<StoreDict> list = storeDictMapper.queryStoreDictList(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<StoreDict> list = storeDictMapper.queryStoreDictList(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询StoreDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public StoreDict queryStoreDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return storeDictMapper.queryStoreDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchStoreDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = storeDictMapper.deleteBatchStoreDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchStoreDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				storeDictMapper.deleteStoreDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteStoreDict\"}";

		}
    }
	
	/**
	 * @Description 更新StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateStoreDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			storeDictMapper.updateStoreDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStoreDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchStoreDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			storeDictMapper.updateBatchStoreDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStoreDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importStoreDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryStoreDictBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<StoreDict> storeList = storeDictMapper.queryStoreDict(entityMap);
		
		List<StoreType> typeList = storeTypeMapper.queryStoreType(entityMap);
		
		if (storeList.size()>0) {
			
			for(StoreType storeType : typeList){
				jsonResult.append("{id:'"+storeType.getType_code()+"',pId:'0',name:'"+storeType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (StoreDict storeDict : storeList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + storeDict.getStore_id() + "',");
				
				jsonResult.append("pId:'"+storeDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + storeDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + storeDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+storeDict.getStore_code()+ " "+ storeDict.getStore_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}

	@Override
	public List<Map<String, Object>> queryStoreDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = storeDictMapper.queryStoreDictPrint(entityMap);
		
		return list;
	}
	
	/**
	 * 用于集团化管理   集团库房选择器
	 */
	@Override
	public String queryGroupStoreDictBySelector(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<StoreDict> storeList = storeDictMapper.queryGroupStoreDict(entityMap);
		
		List<StoreType> typeList = storeTypeMapper.queryGroupStoreType(entityMap);
		
		if (storeList.size()>0) {
			
			for(StoreType storeType : typeList){
				jsonResult.append("{id:'"+storeType.getType_code()+"',pId:'0',name:'"+storeType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (StoreDict storeDict : storeList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + storeDict.getStore_id() + "',");
				
				jsonResult.append("pId:'"+storeDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + storeDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + storeDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+storeDict.getStore_code()+ " "+ storeDict.getStore_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
}
