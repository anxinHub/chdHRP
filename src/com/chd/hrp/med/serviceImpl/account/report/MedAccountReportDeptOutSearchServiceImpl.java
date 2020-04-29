package com.chd.hrp.med.serviceImpl.account.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.MedAccountReportDeptOutSearchMapper;
import com.chd.hrp.med.service.account.report.MedAccountReportDeptOutSearchService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medAccountReportDeptOutSearchService")
public class MedAccountReportDeptOutSearchServiceImpl implements MedAccountReportDeptOutSearchService {
	
	private static Logger logger = Logger.getLogger(MedAccountReportDeptOutServiceImpl.class);
	
	@Resource(name = "medAccountReportDeptOutSearchMapper")
	private final MedAccountReportDeptOutSearchMapper medAccountReportDeptOutSearchMapper = null;
	
	@Override
	public String queryMedAccountReportDeptOutSearch(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medAccountReportDeptOutSearchMapper.queryMedAccountReportDeptOutSearch(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medAccountReportDeptOutSearchMapper.queryMedAccountReportDeptOutSearch(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
