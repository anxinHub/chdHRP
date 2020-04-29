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
import com.chd.hrp.ass.dao.guanLiReports.AssPropertyMonthMainSourceMapper;
import com.chd.hrp.ass.service.guanLiReports.AssPropertyMonthMainSourceService;
import com.github.pagehelper.PageInfo;

@Service("assPropertyMonthMainSourceService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssPropertyMonthMainSourceServiceImpl implements AssPropertyMonthMainSourceService {
	private static Logger logger = Logger.getLogger(AssPropertyMonthMainSourceServiceImpl.class);
	
	@Resource(name = "assPropertyMonthMainSourceMapper")
	private final AssPropertyMonthMainSourceMapper assPropertyMonthMainSourceMapper = null;

	@Override
	public String queryAssPropertyMonthMainSource(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssPropertyMonthMainServiceImpl.queryAssPropertyMonthMainManage");
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = assPropertyMonthMainSourceMapper.queryAssPropertyMonthMainSource(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = assPropertyMonthMainSourceMapper.queryAssPropertyMonthMainSource(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyMonthMainSourcePrint(
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
		List<Map<String, Object>> list = assPropertyMonthMainSourceMapper.queryAssPropertyMonthMainSourcePrint(entityMap);
	
		return list;
	}


}
