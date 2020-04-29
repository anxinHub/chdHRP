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
import com.chd.hrp.ass.dao.guanLiReports.AssNewsDepreciationMapper;
import com.chd.hrp.ass.dao.guanLiReports.AssResourceSetMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet;
import com.chd.hrp.ass.service.guanLiReports.AssNewsDepreciationService;
import com.github.pagehelper.PageInfo;


@Service("assNewsDepreciationService")
public class AssNewsDepreciationServiceImpl implements AssNewsDepreciationService {

	private static Logger logger = Logger.getLogger(AssNewsDepreciationServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assNewsDepreciationMapper")
	private final AssNewsDepreciationMapper assNewsDepreciationMapper = null;
    
	
	
	@Override
	public String queryAssNewsDepreciation(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssResourceSet> list =  null ;
			
			list = assNewsDepreciationMapper.queryAssNewsDepreciation(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}



	@Override
	public List<Map<String, Object>> queryAssNewsDepreciationPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assNewsDepreciationMapper.queryAssNewsDepreciationPrint(entityMap);
	
		return list;
	}
	
	
}
