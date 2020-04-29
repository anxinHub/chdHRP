package com.chd.hrp.ass.serviceImpl.zengjian;

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
import com.chd.hrp.ass.dao.disposal.AssDisposalMainMapper;
import com.chd.hrp.ass.dao.zengjian.AssZengJianMainMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;
import com.chd.hrp.ass.service.zengjian.AssZengJianMainService;
import com.chd.hrp.ass.serviceImpl.zengjian.AssZengJianMainServiceImpl;
import com.github.pagehelper.PageInfo;


@Service("assZengJianMainService")
public class AssZengJianMainServiceImpl implements AssZengJianMainService{
	private static Logger logger = Logger.getLogger(AssZengJianMainServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assZengJianMainMapper")
	private final AssZengJianMainMapper assZengJianMainMapper = null;

	@Override
	public String queryAssZengJian(Map<String, Object> entityMap) throws DataAccessException {
	
		List<Map<String, Object>> list = assZengJianMainMapper.queryAssZengJian(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public List<Map<String, Object>> queryAssZengJianMainPrint(
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
		
		List<Map<String, Object>> list = assZengJianMainMapper.queryAssZengJianMainPrint(entityMap);
	
		return list;
	}
}
