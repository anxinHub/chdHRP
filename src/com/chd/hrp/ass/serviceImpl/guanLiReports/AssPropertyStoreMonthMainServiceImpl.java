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
import com.chd.hrp.ass.dao.guanLiReports.AssPropertyStoreMonthMainMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyStoreMonthMain;
import com.chd.hrp.ass.service.guanLiReports.AssPropertyStoreMonthMainService;
import com.github.pagehelper.PageInfo;

@Service("assPropertyStoreMonthMainService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssPropertyStoreMonthMainServiceImpl implements AssPropertyStoreMonthMainService {
	private static Logger logger = Logger.getLogger(AssPropertyStoreMonthMainServiceImpl.class);
	
	@Resource(name = "assPropertyStoreMonthMainMapper")
	private final AssPropertyStoreMonthMainMapper assPropertyStoreMonthMainMapper = null;

	@Override
	public String queryAssPropertyStoreMonthMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPropertyStoreMonthMain> list = assPropertyStoreMonthMainMapper.queryAssPropertyStoreMonthMain(entityMap);
			 
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPropertyStoreMonthMain> list = assPropertyStoreMonthMainMapper.queryAssPropertyStoreMonthMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyStoreMonthMainPrint(
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
		List<Map<String, Object>> list = assPropertyStoreMonthMainMapper.queryAssPropertyStoreMonthMainPrint(entityMap);
	
		return list;
	}

}
