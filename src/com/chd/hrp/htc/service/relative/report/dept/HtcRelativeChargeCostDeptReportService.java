package com.chd.hrp.htc.service.relative.report.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeChargeCostDeptReportService {
	
	 public String queryHtcRelativeChargeCostDeptReport(Map<String,Object> entityMap)throws DataAccessException;

}
