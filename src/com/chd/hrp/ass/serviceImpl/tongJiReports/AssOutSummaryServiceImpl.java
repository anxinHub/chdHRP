package com.chd.hrp.ass.serviceImpl.tongJiReports;

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
import com.chd.hrp.ass.dao.tongJiReports.AssOutSummaryMapper;
import com.chd.hrp.ass.service.tongJiReports.AssOutSummaryService;
import com.github.pagehelper.PageInfo;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("assOutSummaryService")
public class AssOutSummaryServiceImpl implements AssOutSummaryService {  
	
	private static Logger logger = Logger.getLogger(AssOutSummaryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assOutSummaryMapper")
	private final AssOutSummaryMapper assOutSummaryMapper = null;
	
	@Override
	public String queryAssOutSummary(Map<String, Object> entityMap) throws DataAccessException {  
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assOutSummaryMapper.queryAssOutSummary(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = assOutSummaryMapper.queryAssOutSummary(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssOutMainSummaryPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assOutSummaryMapper.queryAssOutMainSummaryPrint(entityMap);
	
		return list;
	}

	@Override
	public String queryOutSituation(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");  

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assOutSummaryMapper.queryOutSituation(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  assOutSummaryMapper.queryOutSituation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> queryOutSituationPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assOutSummaryMapper.queryOutSituationPrint(entityMap);
	
		return list;
	}

}
