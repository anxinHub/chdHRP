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
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.dao.SupTypeMapper;
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.service.SupDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("supDictService")
public class SupDictServiceImpl implements SupDictService {

	private static Logger logger = Logger.getLogger(SupDictServiceImpl.class);
	
	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
	
	@Resource(name = "supTypeMapper")
	private final SupTypeMapper supTypeMapper = null;
    
	/**
	 * @Description 添加SupDict
	 * @param SupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addSupDict(Map<String,Object> entityMap)throws DataAccessException{
		
		SupDict supDict = querySupDictByCode(entityMap);

		if (supDict != null) {

			return "{\"error\":\"编码：" + supDict.getSup_code().toString() + "已存在.\"}";

		}
		
		try {
			
			if("0".endsWith(entityMap.get("dict_type").toString())){
				 
				supMapper.updateSupByCode(entityMap);
				 
			 }else if("1".endsWith(entityMap.get("dict_type").toString())){
				 
				 supMapper.updateSupByName(entityMap);
				 
			 }
			
			Map<String,Object> map = new HashMap<String,Object>();
			
		    map.put("is_stop", 1);
		    
		    map.put("group_id", entityMap.get("group_id"));
		    
		    map.put("hos_id", entityMap.get("hos_id"));
		    
		    map.put("sup_code", entityMap.get("sup_code"));
		    
		    map.put("sup_id", entityMap.get("sup_id"));
		    
		    supDictMapper.updateSupDictState(map);
		    
			entityMap.put("user_code", SessionManager.getUserCode());
			
			entityMap.put("create_date", new Date());
			
			supDictMapper.addSupDict(entityMap);
			
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSupDict\"}";

		}

	}
	
	/**
	 * @Description 批量添加SupDict
	 * @param  SupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchSupDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			supDictMapper.addBatchSupDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchSupDict\"}";

		}
	}
	
	/**
	 * @Description 查询SupDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querySupDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<SupDict> list = supDictMapper.querySupDict(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public String querySupDictList(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<SupDict> list = supDictMapper.querySupDictList(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<SupDict> list = supDictMapper.querySupDictList(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询SupDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public SupDict querySupDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return supDictMapper.querySupDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchSupDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = supDictMapper.deleteBatchSupDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchSupDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteSupDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				supDictMapper.deleteSupDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteSupDict\"}";

		}
    }
	
	/**
	 * @Description 更新SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateSupDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			supDictMapper.updateSupDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSupDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchSupDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			supDictMapper.updateBatchSupDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSupDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importSupDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String querySupDictBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<SupDict> supList = supDictMapper.querySupDict(entityMap);
		
		List<SupType> typeList = supTypeMapper.querySupType(entityMap);
		
		if (supList.size()>0) {
			
			for(SupType supType : typeList){
				jsonResult.append("{id:'"+supType.getType_code()+"',pId:'0',name:'"+supType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (SupDict supDict : supList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + supDict.getSup_id() + "',");
				
				jsonResult.append("pId:'"+supDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + supDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + supDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+supDict.getSup_code()+ " "+ supDict.getSup_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}

	@Override
	public List<Map<String, Object>> querySupDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = supDictMapper.querySupDictPrint(entityMap);
		
		return list;
	}
	
	/**
	 * 用于 集团化管理   集团供应商选择器
	 */
	@Override
	public String queryGroupSupDictBySelector(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<SupDict> supList = supDictMapper.queryGroupSupDict(entityMap);
		
		List<SupType> typeList = supTypeMapper.queryGroupSupType(entityMap);
		
		if (supList.size()>0) {
			
			for(SupType supType : typeList){
				jsonResult.append("{id:'"+supType.getType_code()+"',pId:'0',name:'"+supType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (SupDict supDict : supList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + supDict.getSup_id() + "',");
				
				jsonResult.append("pId:'"+supDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + supDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + supDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+supDict.getSup_code()+ " "+ supDict.getSup_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
}
