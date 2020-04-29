package com.chd.hrp.ass.serviceImpl.summary;

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
import com.chd.hrp.ass.dao.summary.AssSummaryMapper;
import com.chd.hrp.ass.entity.zengjian.AssZengJianMain;
import com.chd.hrp.ass.service.summary.AssSummaryService;
import com.github.pagehelper.PageInfo;
@Service("assSummaryService")
public class AssSummaryServiceImpl implements AssSummaryService {
	private static Logger logger = Logger.getLogger(AssSummaryServiceImpl.class);

	//引入DAO操作
	@Resource(name = "assSummaryMapper")
	private final AssSummaryMapper assSummaryMapper = null;
			
	@Override
	public String queryAssSummary(Map<String, Object> entityMap)
			throws DataAccessException {
	    		SysPage sysPage = new SysPage();
		
				sysPage = (SysPage) entityMap.get("sysPage");
				
				if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list = assSummaryMapper.queryAssSummary(entityMap);
					
					return ChdJson.toJson(list);
					
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list = assSummaryMapper.queryAssSummary(entityMap, rowBounds);
					
					PageInfo page = new PageInfo(list);
					
					return ChdJson.toJson(list, page.getTotal());
					
				}
	}

	@Override
	public List<Map<String, Object>> queryAssSummaryMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assSummaryMapper.queryAssSummaryMainPrint(entityMap);
	
		return list;
	}

}
