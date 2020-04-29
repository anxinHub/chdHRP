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
import com.chd.hrp.acc.dao.commonbuilder.AccEcoTypeMapper;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.service.commonbuilder.AccEcoTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 支出经济分类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accEcoTypeService")
public class AccEcoTypeServiceImpl implements AccEcoTypeService {

	private static Logger logger = Logger.getLogger(AccEcoTypeServiceImpl.class);
	
	@Resource(name = "accEcoTypeMapper")
	private final AccEcoTypeMapper accEcoTypeMapper = null;
    
	/**
	 * @Description 
	 * 支出经济分类<BR> 添加AccEcoType
	 * @param AccEcoType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccEcoType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AccEcoType> list = accEcoTypeMapper.queryAccEcoTypeById(entityMap);
		
		try {
			
			if (list.size()>0) {
				for(AccEcoType funtype : list ){
					if(funtype.getEco_code().equals(entityMap.get("eco_code"))){
						return "{\"error\":\"编码：" + funtype.getEco_code().toString() + "已存在.\"}";
					}
					if(funtype.getEco_name().equals(entityMap.get("eco_name"))){
						return "{\"error\":\"名称：" + funtype.getEco_name().toString() + "已存在.\"}";
					}
				}
			}
			
			accEcoTypeMapper.addAccEcoType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccEcoType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量添加AccEcoType
	 * @param  AccEcoType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccEcoType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accEcoTypeMapper.addBatchAccEcoType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccEcoType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccEcoType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEcoType> list = accEcoTypeMapper.queryAccEcoType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccEcoType queryAccEcoTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accEcoTypeMapper.queryAccEcoTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量删除AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccEcoType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			String error = "";
			for(Map<String,Object> item : entityList){
				int count = accEcoTypeMapper.queryIsUsed(item);
				if(count>0){
					error +=  item.get("eco_code").toString() + "," ;
				}
			}
			if(error != ""){
				return "{\"error\":\"[支出经济分类编码:" + error.substring(0, error.length()-1) + "]正在被引用,不能删除.\"}"; 
			}else{
				accEcoTypeMapper.deleteBatchAccEcoType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccEcoType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 删除AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccEcoType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accEcoTypeMapper.deleteAccEcoType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccEcoType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 更新AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccEcoType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			accEcoTypeMapper.updateAccEcoType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEcoType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量更新AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccEcoType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accEcoTypeMapper.updateBatchAccEcoType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccEcoType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 导入AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccEcoType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccEcoType(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
			List<AccEcoType> aetList = accEcoTypeMapper.queryAccEcoType(entityMap);
			
			if( aetList.size()>0){
				
				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";
				
			}
			
			List<AccEcoType> accEcoList = accEcoTypeMapper.queryAccEcoTypeByExtend(entityMap);
			
			if(accEcoList.size()>0){
				
				for (AccEcoType accEcoType : accEcoList) {
					
					Map< String, Object> map = new HashMap<String, Object>();
					
					map.put("eco_id", accEcoType.getEco_id());
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_code", SessionManager.getCopyCode());
					
					map.put("acc_year", SessionManager.getAcctYear());
					
					map.put("eco_code", accEcoType.getEco_code());
					
					map.put("eco_name", accEcoType.getEco_name());
					
					map.put("super_code", accEcoType.getSuper_code());
					
					map.put("eco_level", accEcoType.getEco_level());
					
					map.put("is_last", accEcoType.getIs_last());
					
					map.put("spell_code", accEcoType.getSpell_code());
					
					map.put("wbx_code", accEcoType.getWbx_code());
					
					map.put("is_stop", accEcoType.getIs_stop());
					
					map.put("note", accEcoType.getNote());
					
					list.add(map);
					
				}
				
				accEcoTypeMapper.addBatchAccEcoType(list);
				
				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"msg\":\"继承数据不存在，无法继承.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  extendBatchAccEcoType{className}\"}";
		}

	}

	@Override
	public List<Map<String, Object>> qureySurp_code(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accEcoTypeMapper.qureySurp_code(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccEcoTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accEcoTypeMapper.queryAccEcoTypePrint(entityMap);
		return list;
	}
}
