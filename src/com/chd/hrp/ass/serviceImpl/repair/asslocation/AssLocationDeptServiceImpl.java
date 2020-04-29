package com.chd.hrp.ass.serviceImpl.repair.asslocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.asslocation.AssLocationDeptMapper;
import com.chd.hrp.ass.service.repair.asslocation.AssLocationDeptService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;

@Service("assLocationDeptService")
public class AssLocationDeptServiceImpl implements  AssLocationDeptService{
	//引入DAO操作
		@Resource(name = "assLocationDeptMapper")
		private final AssLocationDeptMapper assLocationDeptMapper = null;
		@Resource(name = "matCommonMapper")
		private final MatCommonMapper matCommonMapper = null;    
		
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		/*//获取对象tabledesc
		Map<String,Object> MapVo = queryAssLocationDeptByCode(entityMap);

		if (MapVo != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}*/
	try {
		
		List<String>list=assLocationDeptMapper.queryDeptIdBySuperId(entityMap);
		
		for (String string : list) {
			List<Map<String,Object>>listVo = new ArrayList<Map<String,Object>>();
			entityMap.put("dept_id", string);
			String [] loc_code=  entityMap.get("loc_code").toString().split(",");
			for (String string2 : loc_code) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("loc_code", string2);
				map.put("dept_id", string);
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				listVo.add(map);
			}
			assLocationDeptMapper.addLocationDpt(listVo);
		}
		
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

	}
	catch (DataAccessException e) {

		throw new SysException(e) ;

	}
}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			Map<String,Object>map = assLocationDeptMapper.queryByCode(entityMap);
			if(map!=null){
				return "{\"error\":\"数据已存在.\",\"state\":\"false\"}";
			}
				List<Map<String,Object>>listVo = new ArrayList<Map<String,Object>>();
				assLocationDeptMapper.updateassLocationDept(entityMap);
			
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			throw new SysException(e) ;

		}
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
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
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
	/**
	 * 根据Code筛选数据
	 */
	@Override
	public Map<String, Object> queryAssLocationDeptByCode(Map<String, Object> mapVo) {
		
		Map <String,Object> entityMap = new HashMap<String, Object>();
		entityMap.putAll(mapVo);
		String [] data =entityMap.get("dept_id").toString().split(",");
		entityMap.put("dept_id", data[0]);
		entityMap.put("loc_code", data[2]);
		return assLocationDeptMapper.queryAssLocationDeptByCode(entityMap);
	}
	/**
	 * 主页面tree显示
	 */
	@Override
	public String queryHosDeptTree(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assLocationDeptMapper.queryHosDeptTree(mapVo);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 主页面表格数据显示
	 */
	@Override
	public String queryAssLocationDept(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assLocationDeptMapper.queryAssLocationDept(mapVo);
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 批量删除数据
	 */
	@Override
	public String deleteAssLocationDeptBatch(List<Map<String,Object>> listVo) {
		
		try{
			assLocationDeptMapper.deleteAssLocationDeptBatch(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		
		} catch (Exception e) {
			
		throw new SysException(e);
		} 
	}
}
