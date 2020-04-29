package com.chd.hrp.ass.serviceImpl.repair.statis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.statis.AssRepStatisToUserMapper;
import com.chd.hrp.ass.service.repair.statis.AssRepStatisToUserService;
@Service("assRepStatisToUserService")
public class AssRepStatisToUserServiceImpl implements AssRepStatisToUserService{
	
	@Resource(name="assRepStatisToUserMapper")
	private final AssRepStatisToUserMapper assRepStatisToUserMapper = null;
	
	
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
	public String queryRepTeamTree(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepStatisToUserMapper.queryRepTeamTree(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryRepCountByRepUserId(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepStatisToUserMapper.queryRepCountByRepUserId(mapVo);
		return ChdJson.toJsonLower(list);
	}
	@Override
	public String queryAssRepReportCentreCenter(Map<String, Object> mapVo) {

		List<Map<String,Object>> list=assRepStatisToUserMapper.queryAssRepReportCentreCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryAssRepReportCenter(Map<String, Object> mapVo) {

		List<Map<String,Object>> list=assRepStatisToUserMapper.queryAssRepReportCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}
	
	@Override
	public String queryAssRepUnfinishedCenter(Map<String, Object> mapVo) {

		List<Map<String,Object>> list=assRepStatisToUserMapper.queryAssRepUnfinishedCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}
	
	@Override
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo) {
		
		return assRepStatisToUserMapper.queryAssRepairByCode(mapVo);
	}
	@Override
	public String queryTimeLineRender(Map<String, Object> mapVo) {
		List<Map<String,Object>>list = assRepStatisToUserMapper.queryTimeLineRender(mapVo); 
		return JSONArray.toJSONString(list);
	}
	@Override
	public Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo) {
		
		Map<String, Object> map =assRepStatisToUserMapper.queryCardDataByCode(mapVo);
		
		return map;
	}
	@Override
	public String queryImgUrlByRepCode(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assRepStatisToUserMapper.queryImgUrlByRepCode(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryRepCountByRepDeptId(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assRepStatisToUserMapper.queryRepCountByRepDeptId(mapVo);
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryRepCountByRepCardNo(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assRepStatisToUserMapper.queryRepCountByRepCardNo(mapVo);
		return ChdJson.toJsonLower(list);
	}

}
