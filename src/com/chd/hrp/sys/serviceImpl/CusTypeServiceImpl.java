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
import com.chd.hrp.sys.dao.CusMapper;
import com.chd.hrp.sys.dao.CusTypeMapper;
import com.chd.hrp.sys.entity.Cus;
import com.chd.hrp.sys.entity.CusType;
import com.chd.hrp.sys.service.CusTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("cusTypeService")
public class CusTypeServiceImpl implements CusTypeService {

	private static Logger logger = Logger.getLogger(CusTypeServiceImpl.class);
	
	@Resource(name = "cusTypeMapper")
	private final CusTypeMapper cusTypeMapper = null;
	
	@Resource(name = "cusMapper")
	private final CusMapper cusMapper = null;
    
	/**
	 * @Description 添加CusType
	 * @param CusType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCusType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		CusType cusType = queryCusTypeByCode(entityMap);

		if (cusType != null) {

			return "{\"error\":\"编码：" + cusType.getType_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
		try {
			
			cusTypeMapper.addCusType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCusType\"}";

		}

	}
	
	/**
	 * @Description 批量添加CusType
	 * @param  CusType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCusType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			cusTypeMapper.addBatchCusType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCusType\"}";

		}
	}
	
	/**
	 * @Description 查询CusType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCusType(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CusType> list = cusTypeMapper.queryCusType(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CusType> list = cusTypeMapper.queryCusType(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 查询CusTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CusType queryCusTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return cusTypeMapper.queryCusTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCusType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList) {
					List<Cus> list = cusMapper.queryCus(item);
					if(list.size() > 0) {
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				int state = cusTypeMapper.deleteBatchCusType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCusType\"}";

		}
		
	}
	
		/**
	 * @Description 删除CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCusType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				cusTypeMapper.deleteCusType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCusType\"}";

		}
    }
	
	/**
	 * @Description 更新CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCusType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			cusTypeMapper.updateCusType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCusType\"}";

		}
	}
	
	/**
	 * @Description 批量更新CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCusType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			cusTypeMapper.updateBatchCusType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCusType\"}";

		}
		
	}
	
	/**
	 * @Description 导入CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importCusType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
