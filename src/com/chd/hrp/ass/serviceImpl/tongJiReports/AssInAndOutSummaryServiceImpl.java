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
import com.chd.hrp.ass.dao.tongJiReports.AssInAndOutSummaryMapper;
import com.chd.hrp.ass.dao.tongJiReports.AssOutSummaryMapper;
import com.chd.hrp.ass.service.tongJiReports.AssInAndOutSummaryService;
import com.github.pagehelper.PageInfo;

@Service("assInAndOutSummaryService")
public class AssInAndOutSummaryServiceImpl implements AssInAndOutSummaryService {  

	private static Logger logger = Logger.getLogger(AssInAndOutSummaryServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "assInAndOutSummaryMapper")
	private final AssInAndOutSummaryMapper assInAndOutSummaryMapper = null;
		
	@Override
	public String queryAssInAndOutSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assInAndOutSummaryMapper.queryAssInAndOutSummary(entityMap);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = assInAndOutSummaryMapper.queryAssInAndOutSummary(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryAssInDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> detailList = assInAndOutSummaryMapper.queryAssInDetail(mapVo);
		
		return ChdJson.toJson(detailList);
	}

	@Override
	public String queryAssOutDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> detailList = assInAndOutSummaryMapper.queryAssOutDetail(mapVo);
		
		return ChdJson.toJson(detailList);
	}

	@Override
	public List<Map<String, Object>> queryAssInAndOutSummaryPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assInAndOutSummaryMapper.queryAssInAndOutSummary(entityMap);
	
		return list;
	}
}
