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
import com.chd.hrp.sys.dao.CusDictMapper;
import com.chd.hrp.sys.dao.CusMapper;
import com.chd.hrp.sys.dao.CusTypeMapper;
import com.chd.hrp.sys.entity.CusDict;
import com.chd.hrp.sys.entity.CusType;
import com.chd.hrp.sys.service.CusDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("cusDictService")
public class CusDictServiceImpl implements CusDictService {

	private static Logger logger = Logger.getLogger(CusDictServiceImpl.class);
	
	@Resource(name = "cusDictMapper")
	private final CusDictMapper cusDictMapper = null;
	
	@Resource(name = "cusMapper")
	private final CusMapper cusMapper = null;
	
	@Resource(name = "cusTypeMapper")
	private final CusTypeMapper cusTypeMapper = null;
    
	/**
	 * @Description 添加CusDict
	 * @param CusDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCusDict(Map<String,Object> entityMap)throws DataAccessException{
		
		CusDict cusDict = queryCusDictByCode(entityMap);

		if (cusDict != null) {

			return "{\"error\":\"编码：" + cusDict.getCus_code() + "已存在.\"}";

		}
		
		try {
			
			 if("0".endsWith(entityMap.get("dict_type").toString())){
				 
				 cusMapper.updateCusByCode(entityMap);
				 
			 }else if("1".endsWith(entityMap.get("dict_type").toString())){
				 
				 cusMapper.updateCusByName(entityMap);
				 
			 }
			 
			Map<String,Object> map = new HashMap<String,Object>();
			
		    map.put("is_stop", 1);
		    
		    map.put("group_id", entityMap.get("group_id"));
		    
		    map.put("hos_id", entityMap.get("hos_id"));
		    
		    map.put("cus_code", entityMap.get("cus_code"));
		    
		    map.put("cus_id", entityMap.get("cus_id"));
		    
		    cusDictMapper.updateCusDictState(map);
		    
			entityMap.put("user_code", SessionManager.getUserCode());
			
			entityMap.put("create_date", new Date());
			
			cusDictMapper.addCusDict(entityMap);
			
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCusDict\"}";

		}

	}
	
	/**
	 * @Description 批量添加CusDict
	 * @param  CusDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCusDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			cusDictMapper.addBatchCusDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCusDict\"}";

		}
	}
	
	/**
	 * @Description 查询CusDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCusDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<CusDict> list = cusDictMapper.queryCusDict(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public String queryCusDictList(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CusDict> list = cusDictMapper.queryCusDictList(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CusDict> list = cusDictMapper.queryCusDictList(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询CusDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CusDict queryCusDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return cusDictMapper.queryCusDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCusDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = cusDictMapper.deleteBatchCusDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCusDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCusDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				cusDictMapper.deleteCusDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCusDict\"}";

		}
    }
	
	/**
	 * @Description 更新CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCusDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			cusDictMapper.updateCusDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCusDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCusDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			cusDictMapper.updateBatchCusDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCusDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importCusDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryCusDictBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<CusDict> cusList = cusDictMapper.queryCusDict(entityMap);
		
		List<CusType> typeList = cusTypeMapper.queryCusType(entityMap);
		
		if (cusList.size()>0) {
			
			for(CusType cusType : typeList){
				jsonResult.append("{id:'"+cusType.getType_code()+"',pId:'0',name:'"+cusType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (CusDict cusDict : cusList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + cusDict.getCus_id() + "',");
				
				jsonResult.append("pId:'"+cusDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + cusDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + cusDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+cusDict.getCus_code()+ " "+ cusDict.getCus_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}

	@Override
	public List<Map<String, Object>> queryCusDictPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = cusDictMapper.queryCusDictPrint(entityMap);
		
		return list;
	}
	
	/**
	 * 用于集团化管理   集团化客户选择器
	 */
	@Override
	public String queryGroupCusDictBySelector(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<CusDict> cusList = cusDictMapper.queryGroupCusDict(entityMap);
		
		List<CusType> typeList = cusTypeMapper.queryGroupCusType(entityMap);
		
		if (cusList.size()>0) {
			
			for(CusType cusType : typeList){
				jsonResult.append("{id:'"+cusType.getType_code()+"',pId:'0',name:'"+cusType.getType_name()+"',open:true},");
			}
			
			int row = 0;
			
			for (CusDict cusDict : cusList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + cusDict.getCus_id() + "',");
				
				jsonResult.append("pId:'"+cusDict.getType_code()+"',");
				
				jsonResult.append("group_id:'" + cusDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + cusDict.getHos_id() + "',");
				
				jsonResult.append("name:'"+cusDict.getCus_code()+ " "+ cusDict.getCus_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
}
