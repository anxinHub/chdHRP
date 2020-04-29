package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.MatAccountReportDeptOutSearchMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAccountReportDeptOutSearchService")
public class MatAccountReportDeptOutSearchServiceImpl implements MatAccountReportDeptOutSearchService {
	
	private static Logger logger = Logger.getLogger(MatAccountReportDeptOutServiceImpl.class);
	
	@Resource(name = "matAccountReportDeptOutSearchMapper")
	private final MatAccountReportDeptOutSearchMapper matAccountReportDeptOutSearchMapper = null;
	
	@Override
	public String queryMatAccountReportDeptOutSearch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearch(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearch(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAccountReportDeptOutSearchByCollect(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearchByCollect(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearchByCollect(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
		
	}

	@Override
	public List<Map<String, Object>> queryMatAccountReportDeptOutSearchPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String,Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearch(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatAccountReportDeptOutSearchByCollectPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matAccountReportDeptOutSearchMapper.queryMatAccountReportDeptOutSearchByCollect(entityMap);
		return list;
	}

}
