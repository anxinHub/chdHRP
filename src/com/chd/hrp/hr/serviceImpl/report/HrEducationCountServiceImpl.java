package com.chd.hrp.hr.serviceImpl.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.report.HrEducationCountMapper;
import com.chd.hrp.hr.service.report.HrEducationCountService;
@Service("hrEducationCountService")
public class HrEducationCountServiceImpl implements HrEducationCountService {
	
	@Resource(name="hrEducationCountMapper")
	private final HrEducationCountMapper hrEducationCountMapper = null;
	
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
	public String queryHrEducationCount(Map<String, Object> mapVo) {
		
		List<Map<String,Object>>list=hrEducationCountMapper.queryHrEducationCount(mapVo);
		
		return ChdJson.toJsonLower(list);
	}
	@Override
	public List<Map<String,Object>> queryHrEducationCountPrint(Map<String, Object> mapVo) {
		
		List<Map<String,Object>>list=hrEducationCountMapper.queryHrEducationCount(mapVo);
		
		return list;
	}

	@Override
	public String queryHrAgeCount(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrAgeCount(mapVo);
		return  ChdJson.toJsonLower(list);
	}
	@Override
	public List<Map<String,Object>> queryHrAgeCountPrint(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrAgeCount(mapVo);
		return  list;
	}

	@Override
	public String queryHrWorkAgeCount(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrWorkAgeCount(mapVo);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public List<Map<String, Object>> queryHrWorkAgeCountPrint(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrWorkAgeCount(mapVo);
		return  list;
	}

	@Override
	public String queryHrUserCountMain(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrUserCountMain(mapVo);
		return  ChdJson.toJsonLower(list);
	}

	@Override
	public List<Map<String, Object>> queryHrUserCountMainPrint(Map<String, Object> mapVo) {
		List<Map<String,Object>>list= hrEducationCountMapper.queryHrUserCountMain(mapVo);
		return  list;
	}

	@Override
	public Map<String,Object> queryDegreeColumns(Map<String, Object> mapVo) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> Degreelist = hrEducationCountMapper.queryDegreeColumns(mapVo);
		List<Map<String,Object>> Stationlist = hrEducationCountMapper.queryStationColumns(mapVo);
		map.put("stationList", Stationlist);
		map.put("degreeList", Degreelist);
		return  map;
	}

}
