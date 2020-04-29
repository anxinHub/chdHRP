package com.chd.hrp.htc.service.task.report.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskChargeCostHosReportService {

	 public String queryHtcTaskChargeCostHosReport(Map<String,Object> entityMap)throws DataAccessException;
}
