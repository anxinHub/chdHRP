package com.chd.hrp.htc.serviceImpl.income.report.dept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.income.report.dept.HtcIncomeChargeCostDeptReportMapper;
import com.chd.hrp.htc.service.income.report.dept.HtcIncomeChargeCostDeptReportService;
import com.github.pagehelper.PageInfo;

@Service("htcIncomeChargeCostDeptReportService")
public class HtcIncomeChargeCostDeptReportServiceImpl implements HtcIncomeChargeCostDeptReportService{
	
	private static Logger logger = Logger.getLogger(HtcIncomeChargeCostDeptReportServiceImpl.class);
	
	@Resource(name = "htcIncomeChargeCostDeptReportMapper")
	private final HtcIncomeChargeCostDeptReportMapper htcIncomeChargeCostDeptReportMapper = null;

	
	@Override
	public String queryHtcIncomeChargeCostDeptReport(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = htcIncomeChargeCostDeptReportMapper.queryHtcIncomeChargeCostDeptReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = htcIncomeChargeCostDeptReportMapper.queryHtcIncomeChargeCostDeptReport(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
