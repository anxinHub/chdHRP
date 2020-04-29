package com.chd.hrp.htc.serviceImpl.relative.report.dept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.relative.report.dept.HtcRelativeChargeCostDeptReportMapper;
import com.chd.hrp.htc.service.relative.report.dept.HtcRelativeChargeCostDeptReportService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativeChargeCostDeptReportService")
public class HtcRelativeChargeCostDeptReportServiceImpl implements HtcRelativeChargeCostDeptReportService{
	
	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostDeptReportServiceImpl.class);
	
	@Resource(name = "htcRelativeChargeCostDeptReportMapper")
	private final HtcRelativeChargeCostDeptReportMapper htcRelativeChargeCostDeptReportMapper = null;

	
	@Override
	public String queryHtcRelativeChargeCostDeptReport(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = htcRelativeChargeCostDeptReportMapper.queryHtcRelativeChargeCostDeptReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = htcRelativeChargeCostDeptReportMapper.queryHtcRelativeChargeCostDeptReport(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
