package com.chd.hrp.ass.serviceImpl.guanLiReports;

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
import com.chd.hrp.ass.dao.guanLiReports.AssPropertyMonthBusMainMapper;
import com.chd.hrp.ass.dao.guanLiReports.AssPropertyMonthMainMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyMonthMain;
import com.chd.hrp.ass.service.guanLiReports.AssPropertyMonthMainService;
import com.github.pagehelper.PageInfo;

@Service("assPropertyMonthMainService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssPropertyMonthMainServiceImpl implements AssPropertyMonthMainService {
	private static Logger logger = Logger.getLogger(AssPropertyMonthMainServiceImpl.class);
	
	@Resource(name = "assPropertyMonthMainMapper")
	private final AssPropertyMonthMainMapper assPropertyMonthMainMapper = null;
	
	@Resource(name = "assPropertyMonthBusMainMapper")
	private final AssPropertyMonthBusMainMapper assPropertyMonthBusMainMapper = null;

	@Override
	public String queryAssPropertyMonthMain(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssPropertyMonthMainServiceImpl.queryAssPropertyMonthMainManage");
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPropertyMonthMain> list = assPropertyMonthMainMapper.queryAssPropertyMonthMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPropertyMonthMain> list = assPropertyMonthMainMapper.queryAssPropertyMonthMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyMonthMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month") != null){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assPropertyMonthMainMapper.queryAssPropertyMonthMainPrint(entityMap);
	
		return list;
	}

	@Override
	public String queryAssPropertyBusTypeMonthMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assPropertyMonthMainMapper.queryAssPropertyBusTypeMonthMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{ 
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = assPropertyMonthMainMapper.queryAssPropertyBusTypeMonthMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyBusTypeMonthMainPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month") != null){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assPropertyMonthMainMapper.queryAssPropertyBusTypeMonthMain(entityMap);
	
		return list;
	}

	@Override
	public String queryAssPropertyBusMonthMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assPropertyMonthBusMainMapper.queryAssPropertyBusMonthMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{ 
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = assPropertyMonthBusMainMapper.queryAssPropertyBusMonthMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyBusMonthMainPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month") != null){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assPropertyMonthBusMainMapper.queryAssPropertyBusMonthMain(entityMap);
	
		return list;
	}

}
