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
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.InfoDict;
import com.chd.hrp.sys.service.InfoDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("infoDictService")
public class InfoDictServiceImpl implements InfoDictService {

	private static Logger logger = Logger.getLogger(InfoDictServiceImpl.class);
	
	@Resource(name = "infoDictMapper")
	private final InfoDictMapper infoDictMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
    
	/**
	 * @Description 添加InfoDict
	 * @param InfoDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addInfoDict(Map<String,Object> entityMap)throws DataAccessException{
		
		InfoDict infoDict = queryInfoDictByCode(entityMap);

		if (infoDict != null) {

			return "{\"error\":\"编码：" + infoDict.getHos_code() + "已存在.\"}";

		}
		
		try {
			 	Map<String,Object> map = new HashMap<String,Object>();
			    map.put("is_stop", 1);
			    map.put("group_id", entityMap.get("group_id"));
			    map.put("hos_id", entityMap.get("hos_id"));
			    infoDictMapper.updateInfoDictState(map);
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				
				Map<String,Object> utilMap=new HashMap<String,Object>();
				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("field_table","hos_info");
				utilMap.put("field_sort", "hos_sort");
				int count=sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("hos_sort",count);
				
				infoDictMapper.addInfoDict(entityMap);
			
			return "{\"msg\":\"变更成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addInfoDict\"}";

		}

	}
	
	/**
	 * @Description 批量添加InfoDict
	 * @param  InfoDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchInfoDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			infoDictMapper.addBatchInfoDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchInfoDict\"}";

		}
	}
	
	/**
	 * @Description 查询InfoDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryInfoDict(Map<String,Object> entityMap) throws DataAccessException{
		
		List<InfoDict> list = infoDictMapper.queryInfoDict(entityMap);
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 查询InfoDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public InfoDict queryInfoDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return infoDictMapper.queryInfoDictByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除InfoDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchInfoDict(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = infoDictMapper.deleteBatchInfoDict(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchInfoDict\"}";

		}
		
	}
	
		/**
	 * @Description 删除InfoDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				infoDictMapper.deleteInfoDict(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteInfoDict\"}";

		}
    }
	
	/**
	 * @Description 更新InfoDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateInfoDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			infoDictMapper.updateInfoDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateInfoDict\"}";

		}
	}
	
	/**
	 * @Description 批量更新InfoDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchInfoDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			infoDictMapper.updateBatchInfoDict(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateInfoDict\"}";

		}
		
	}
	
	/**
	 * @Description 导入InfoDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importInfoDict(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	/**
	 * @Description 查询InfoDictByMenu
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryInfoDictByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<InfoDict> list = infoDictMapper.queryInfoDict(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (InfoDict info : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + info.getHos_id() + ",");
				jsonResult.append("group_id:" + info.getGroup_id() + ",");
				//jsonResult.append("pId:" + info.getSuper_code() + ",");
				jsonResult.append("name:'" + info.getHos_simple()+ "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	@Override
	public Map<String, Object> queryHosInfoToGroupInfo(Map<String, Object> entityMap) throws DataAccessException {
		return infoDictMapper.queryHosInfoToGroupInfo(entityMap);
	}

	@Override
	public String queryHosInfoList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<InfoDict> list = infoDictMapper.queryHosInfoList(entityMap);
			
			return ChdJson.toJson(list);
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<InfoDict> list = infoDictMapper.queryHosInfoList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryHosInfoListPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = infoDictMapper.queryHosInfoListPrint(entityMap);
		
		return list;
	}
}
