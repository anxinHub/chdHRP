package com.chd.hrp.htc.service.task.report.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskChargeCostDeptReportService {
	
	 public String queryHtcTaskChargeCostDeptReport(Map<String,Object> entityMap)throws DataAccessException;
}
