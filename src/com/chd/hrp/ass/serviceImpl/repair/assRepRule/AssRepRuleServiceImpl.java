package com.chd.hrp.ass.serviceImpl.repair.assRepRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.assRepRule.AssRepRuleMapper;
import com.chd.hrp.ass.service.repair.assRepRule.AssRepRuleService;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Service("assRepRuleService")
public class AssRepRuleServiceImpl implements AssRepRuleService {

	@Resource(name = "assRepRuleMapper")
	private final AssRepRuleMapper assRepRuleMapper = null;

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

	@Override
	public String queryRepTeamUser(Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> list = assRepRuleMapper.queryRepTeamUser(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String saveRepRule(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<String> list = Json.fromJsonAsList(String.class, mapVo.get("id").toString());
			assRepRuleMapper.deleteRepRule(mapVo);
			List<String> id = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				id.add(list.get(j));
				if(id.size()>=1000){
					assRepRuleMapper.saveRepRule(mapVo,id);
					id.removeAll(id);
				}
			}
			if(id.size()!=0){
				assRepRuleMapper.saveRepRule(mapVo,id);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	@Override
	public String queryAsscardTree(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepRuleMapper.queryAsscardTree(mapVo);


		return ChdJson.toJson(list);
	}

	@Override
	public String queryAssTypeTree(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepRuleMapper.queryAssTypeTree(mapVo);
		
		
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryAssFaultTypeTree(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = assRepRuleMapper.queryAssFaultTypeTree(mapVo);
		
		
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryTreeDataByUserId(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepRuleMapper.queryTreeDataByUserId(mapVo);
		return JSONArray.toJSONString(list);
	}
}
