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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.FacMapper;
import com.chd.hrp.sys.dao.FacTypeMapper;
import com.chd.hrp.sys.entity.Fac;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.service.FacTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("facTypeService")
public class FacTypeServiceImpl implements FacTypeService {

	private static Logger logger = Logger.getLogger(FacTypeServiceImpl.class);
	
	@Resource(name = "facTypeMapper")
	private final FacTypeMapper facTypeMapper = null;
    
	@Resource(name = "facMapper")
	private final FacMapper facMapper = null;
	/**
	 * @Description 添加FacType
	 * @param FacType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addFacType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
		FacType facType = queryFacTypeByCode(entityMap);

		if (facType != null) {

			return "{\"error\":\"编码：" + facType.getType_code() + "已存在.\"}";

		}
		
		try {
			
			facTypeMapper.addFacType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFacType\"}";

		}

	}
	
	/**
	 * @Description 批量添加FacType
	 * @param  FacType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchFacType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			facTypeMapper.addBatchFacType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchFacType\"}";

		}
	}
	
	/**
	 * @Description 查询FacType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryFacType(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<FacType> list = facTypeMapper.queryFacType(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<FacType> list = facTypeMapper.queryFacType(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 查询FacTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public FacType queryFacTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return facTypeMapper.queryFacTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchFacType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item :entityList ){
					List<Fac>  list = facMapper.queryFac(item);
					if(list.size() > 0 ){
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}

				int state = facTypeMapper.deleteBatchFacType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchFacType\"}";

		}
		
	}
	
		/**
	 * @Description 删除FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteFacType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				facTypeMapper.deleteFacType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteFacType\"}";

		}
    }
	
	/**
	 * @Description 更新FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateFacType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			facTypeMapper.updateFacType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFacType\"}";

		}
	}
	
	/**
	 * @Description 批量更新FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchFacType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			facTypeMapper.updateBatchFacType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFacType\"}";

		}
		
	}
	
	/**
	 * @Description 导入FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importFacType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String importReadFacTypeFiles(Map<String, Object> mapVo) {
		int addCount = 0;
		try {

			List<Map> addList = new ArrayList<Map>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("type_code", map.get("type_code").get(1));
						saveMap.put("type_name", map.get("type_name").get(1));
						if (map.get("is_stop").get(1).toString().equals("是")) {
							saveMap.put("is_stop", 1);
						}else {
							saveMap.put("is_stop", 0);
						}
						
						if (map.get("note").get(1) != null) {
							saveMap.put("note", map.get("note").get(1));
						}else {
							saveMap.put("note", null);
						}
						
						saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("type_name").toString()));
						saveMap.put("wbx_code", StringTool.toWuBi(map.get("type_name").toString()));
						addList.add(saveMap);
				}
				if (addList.size() > 0) {
					//查询是否存在重复的编号
					List<String> reStr = facTypeMapper.queryImportReadFacTypeFiles(addList,mapVo);
					if(reStr.size() > 0){
						throw new SysException("编号已存在："+reStr.toString());
					}
					
					//批量插入
					addCount = facTypeMapper.importReadFacTypeFiles(addList,mapVo);
					if (addCount == 0) {
						throw new SysException("导入失败,请重试!");
					}
				}
			}
			return "{\"msg\":\"成功导入 "+addCount+" 条数据!\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage(),e);
		}
	}
}
