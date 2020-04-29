package com.chd.hrp.htc.service.income.report.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeChargeCostHosReportService {
	
	 public String queryHtcIncomeChargeCostHosReport(Map<String,Object> entityMap)throws DataAccessException;

}
