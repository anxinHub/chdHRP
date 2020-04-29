package com.chd.hrp.ass.serviceImpl.biandongmonthly;

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
import com.chd.hrp.ass.dao.biandongmonthly.AssMonthlyReportDeptMapper;
import com.chd.hrp.ass.service.biandongmonthly.AssMonthlyReportDeptService;
import com.github.pagehelper.PageInfo;
@Service("assMonthlyReportDeptService")
public class AssMonthlyReportDeptServiceImpl implements AssMonthlyReportDeptService{
	private static Logger logger = Logger.getLogger(AssMonthlyReportDeptServiceImpl.class);

	//引入DAO操作
			@Resource(name = "assMonthlyReportDeptMapper")
			private final AssMonthlyReportDeptMapper assMonthlyReportDeptMapper = null;

			@Override
			public String queryAssMonthlyReportDept(Map<String, Object> entityMap)
					throws DataAccessException {
				SysPage sysPage = new SysPage();
				sysPage = (SysPage) entityMap.get("sysPage");
				if (sysPage.getTotal()==-1){
					List<Map<String, Object>> list = assMonthlyReportDeptMapper.queryPaySearch(entityMap);
					return ChdJson.toJson(list);
				}else{
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					List<Map<String, Object>> list = assMonthlyReportDeptMapper.queryPaySearch(entityMap, rowBounds);
					PageInfo page = new PageInfo(list);
					return ChdJson.toJson(list, page.getTotal());
				}
			}

			@Override
			public List<Map<String, Object>> queryAssMonthlyReportMainPrint(
					Map<String, Object> entityMap) throws DataAccessException {
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
				
				List<Map<String, Object>> list = assMonthlyReportDeptMapper.queryAssMonthlyReportMainPrint(entityMap);
			
				return list;
			}
			
			
			
			
}
