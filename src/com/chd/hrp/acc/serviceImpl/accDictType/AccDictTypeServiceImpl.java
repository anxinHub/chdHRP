package com.chd.hrp.acc.serviceImpl.accDictType;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccElementAnalyzeMapper;
import com.chd.hrp.acc.dao.accDictType.AccDictTypeMapper;
import com.chd.hrp.acc.service.AccElementAnalyzeService;
import com.chd.hrp.acc.service.accDictType.AccDictTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("accDictTypeService")
public class AccDictTypeServiceImpl implements AccDictTypeService {

	private static Logger logger = Logger.getLogger(AccDictTypeServiceImpl.class);

	@Resource(name = "accDictTypeMapper")
	private final AccDictTypeMapper accDictTypeMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	//查询
	@Override
	public String queryDict(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		mapVo.put("mod_code", SessionManager.getModCode());
		if( !(mapVo.containsKey("yw_type")) ) {
			mapVo.put("yw_type", 01001);
		}
		List<Map<String, Object>> list = accDictTypeMapper.queryDictMp(mapVo);
		
		PageInfo page = new PageInfo(list);
		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	public List<Map<String, Object>> queryDictPrint(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		mapVo.put("mod_code", SessionManager.getModCode());
		if( !(mapVo.containsKey("yw_type")) ) {
			mapVo.put("yw_type", 01001);
		}
		List<Map<String, Object>> list = accDictTypeMapper.queryDictMp(mapVo);
		return  list ;
	}


	//增加
	@Override
	public String addDict(Map<String, Object> entityMap) {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());  

		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("field_table", "hos_dict_type");
		utilMap.put("field_key1", "table_code");
		utilMap.put("field_value1", entityMap.get("table_code"));
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");

		if (entityMap.get("sort_code").equals("系统生成")) {
			utilMap.remove("field_key2");
			utilMap.put("field_sort", "sort_code");
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("sort_code", count);
		}
		
		if( !(entityMap.containsKey("table_code")) || entityMap.get("table_code").equals("") || entityMap.get("table_code")==null ) {
			return "{\"error\":\"业务类型不能为空\",\"status\":false}";
		}
		if( !(entityMap.containsKey("dict_code")) || entityMap.get("dict_code").equals("") || entityMap.get("dict_code")==null ) {
			return "{\"error\":\"字典编码不能为空\",\"status\":false}";
		}
		if( !(entityMap.containsKey("dict_name")) || entityMap.get("dict_name").equals("") || entityMap.get("dict_name")==null ) {
			return "{\"error\":\"编码名称不能为空\",\"status\":false}";
		}
		if( !(entityMap.containsKey("is_stop")) || entityMap.get("is_stop").equals("") || entityMap.get("is_stop")==null ) {
			return "{\"error\":\"启用状态不能为空\",\"status\":false}";
		}
		
		try {
			
			List<Map<String, Object>> list = accDictTypeMapper.queryDictCodeMp(entityMap);
			
			if(list.isEmpty() || list==null) {
				int addCount = accDictTypeMapper.addDictMp(entityMap);
				if (addCount == 0) {
					throw new SysException("保存失败,请刷新尝试!");
				}
				return "{\"msg\":\"添加成功\",\"status\":true}";
			}else {
				return "{\"error\":\"该字典编码已存在\",\"status\":false}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	//删除
	@Override
	public String deleteDict(Map<String, Object> mapVo) {

		mapVo.put("group_id", SessionManager.getGroupId()); 
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try { if(mapVo.containsKey("dict_code")) { 
			if( mapVo.get("dict_code")==null || mapVo.get("dict_code").equals("") ) { 
				return "{\"error\":\"删除失败,没有字典编码\",\"isSuc\":false}"; 
			} 
			}else { 
				return "{\"error\":\"删除失败,没有字典编码\",\"isSuc\":false}"; 
			}
			if(mapVo.containsKey("table_code")) { 
				if( mapVo.get("table_code")==null || mapVo.get("table_code").equals("") ) {
					return "{\"error\":\"删除失败,没有字业务类型\",\"isSuc\":false}"; 
				} 
			}else {
				return "{\"error\":\"删除失败,没有业务类型\",\"isSuc\":false}"; 
			}
			int addCount = accDictTypeMapper.deleteDictMp(mapVo);
			if (addCount == 0) { 
				throw new SysException("保存失败,请刷新尝试!"); 
			} 
			return "{\"msg\":\"删除成功\",\"isSuc\":true}"; 
		} catch (Exception e) {
			logger.error(e.getMessage(),e); throw new SysException(); }
	}

	//修改
	@Override
	public String updateDict(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());  
		try {
			int addCount = accDictTypeMapper.updateDictMp(mapVo);

			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"修改成功\",\"status\":true}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}


	//打印
	@Override
	public List<Map<String, Object>> queryAccElementPrint(Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> list = accDictTypeMapper.queryDictPrintMp(mapVo);
		return list;
	}
	

	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			String dict_code=null;
			String[] code=null;
			for(Map<String,Object> map:entityList){
				dict_code=map.get("dict_code").toString();
				code=dict_code.split("@");
				map.put("dict_code", code[0]);
				map.put("table_code", code[1]);
			}
			accDictTypeMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}


	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
