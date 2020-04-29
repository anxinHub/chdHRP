package com.chd.hrp.htc.service.relative.report.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeChargeCostHosReportService {
	
	 public String queryHtcRelativeChargeCostHosReport(Map<String,Object> entityMap)throws DataAccessException;

}
