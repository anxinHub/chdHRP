/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.commonbuilder;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccFunTypeMapper;
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.entity.AccFunType;
import com.chd.hrp.acc.service.commonbuilder.AccFunTypeService;
import com.chd.hrp.sys.entity.Dept;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 支出功能分类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accFunTypeService")
public class AccFunTypeServiceImpl implements AccFunTypeService {

	private static Logger logger = Logger.getLogger(AccFunTypeServiceImpl.class);
	
	@Resource(name = "accFunTypeMapper")
	private final AccFunTypeMapper accFunTypeMapper = null;
    
	/**
	 * @Description 
	 * 支出功能分类<BR> 添加AccFunType
	 * @param AccFunType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccFunType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AccFunType> list = accFunTypeMapper.queryAccFunTypeById(entityMap);

		try {
			if (list.size()>0) {
				for(AccFunType funtype : list ){
					if(funtype.getFun_code().equals(entityMap.get("fun_code"))){
						return "{\"error\":\"编码：" + funtype.getFun_code().toString() + "已存在.\"}";
					}
					if(funtype.getFun_name().equals(entityMap.get("fun_name"))){
						return "{\"error\":\"名称：" + funtype.getFun_name().toString() + "已存在.\"}";
					}
				}
			}
			
			accFunTypeMapper.addAccFunType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccFunType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量添加AccFunType
	 * @param  AccFunType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccFunType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accFunTypeMapper.addBatchAccFunType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccFunType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccFunType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccFunType> list = accFunTypeMapper.queryAccFunType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccFunType queryAccFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accFunTypeMapper.queryAccFunTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量删除AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccFunType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			String error ="";
			for(Map<String,Object> item : entityList){
				int count = accFunTypeMapper.queryIsUsed(item);
				
				if(count>0){
					error += item.get("fun_code").toString() +",";
					
				}
			}
			if(error !=""){
				return "{\"error\":\"[支出功能分类编码:" + error.substring(0, error.length()-1) + "]正在被引用,不能删除.\"}"; 
			}else{
				accFunTypeMapper.deleteBatchAccFunType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccFunType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 删除AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccFunType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accFunTypeMapper.deleteAccFunType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccFunType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 更新AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccFunType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			accFunTypeMapper.updateAccFunType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccFunType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量更新AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccFunType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accFunTypeMapper.updateBatchAccFunType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccFunType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 导入AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccFunType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccFunType(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
			List<AccFunType> aftList = accFunTypeMapper.queryAccFunType(entityMap);
			
			if( aftList.size()>0){
				
				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";
				
			}
			
			List<AccFunType> accFunList = accFunTypeMapper.queryAccFunTypeByExtend(entityMap);
			
			if(accFunList.size()>0){
				
				for (AccFunType accFunType : accFunList) {
					
					Map< String, Object> map = new HashMap<String, Object>();
					
					map.put("fun_id", accFunType.getFun_id());
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_code", SessionManager.getCopyCode());
					
					map.put("acc_year", SessionManager.getAcctYear());
					
					map.put("fun_code", accFunType.getFun_code());
					
					map.put("fun_name", accFunType.getFun_name());
					
					map.put("super_code", accFunType.getSuper_code());
					
					map.put("fun_level", accFunType.getFun_level());
					
					map.put("is_last", accFunType.getIs_last());
					
					map.put("spell_code", accFunType.getSpell_code());
					
					map.put("wbx_code", accFunType.getWbx_code());
					
					map.put("is_stop", accFunType.getIs_stop());
					
					map.put("note", accFunType.getNote());
					
					list.add(map);
					
				}
				
				accFunTypeMapper.addBatchAccFunType(list);
				
				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
				
			}
			
			return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  extendBatchAccFunType{className}\"}";
			
		}


	}

	@Override
	public List<Map<String, Object>> qureySurp_code(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return accFunTypeMapper.qureySurp_code(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccFunTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accFunTypeMapper.queryAccFunTypePrint(entityMap);
		return list;
	}
}
