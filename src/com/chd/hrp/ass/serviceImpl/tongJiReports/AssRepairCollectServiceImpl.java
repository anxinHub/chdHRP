package com.chd.hrp.ass.serviceImpl.tongJiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.tongJiReports.AssRepairCollectMapper;
import com.chd.hrp.ass.service.tongJiReports.AssRepairCollectService;

@Service("assRepairCollectService")
public class AssRepairCollectServiceImpl implements AssRepairCollectService{

	@Resource(name="assRepairCollectMapper")
	private final AssRepairCollectMapper assRepairCollectMapper = null;
	
	
	@Override
	public String queryAssRepairCollect(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> listVo =assRepairCollectMapper.queryAssRepairCollect(mapVo);
		
		return ChdJson.toJson(listVo);
	}


	@Override
	public List<Map<String, Object>> queryAssRepairCollectPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assRepairCollectMapper.queryAssRepairCollectPrint(entityMap);
	
		return list;
	}

}
