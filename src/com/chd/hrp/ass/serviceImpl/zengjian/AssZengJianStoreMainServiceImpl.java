package com.chd.hrp.ass.serviceImpl.zengjian;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.zengjian.AssZengJianStoreMainMapper;
import com.chd.hrp.ass.service.zengjian.AssZengJianStoreMainService;


@Service("assZengJianStoreMainService")
public class AssZengJianStoreMainServiceImpl implements AssZengJianStoreMainService{
	private static Logger logger = Logger.getLogger(AssZengJianStoreMainServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assZengJianStoreMainMapper")
	private final AssZengJianStoreMainMapper assZengJianStoreMainMapper = null;

	@Override
	public String queryAssZengJianStore(Map<String, Object> entityMap) throws DataAccessException {
	
		List<Map<String, Object>> list = assZengJianStoreMainMapper.queryAssZengJianStore(entityMap);
		
		return ChdJson.toJson(list);
	}
 
	@Override
	public List<Map<String, Object>> queryAssZengJianStoreMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month")!= null ){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		
		List<Map<String, Object>> list = assZengJianStoreMainMapper.queryAssZengJianStoreMainPrint(entityMap);
	
		return list;
	}
}
