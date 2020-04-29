package com.chd.hrp.htc.service.income.report.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeChargeCostDeptReportService {
	
	 public String queryHtcIncomeChargeCostDeptReport(Map<String,Object> entityMap)throws DataAccessException;

}
