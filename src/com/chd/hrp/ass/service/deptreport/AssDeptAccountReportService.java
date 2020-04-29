package com.chd.hrp.ass.service.deptreport;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssDeptAccountReportService extends SqlService{
	public String query(Map<String, Object> page)  throws DataAccessException ;
	
	public String queryDetail(Map<String, Object> page)  throws DataAccessException ;
	
	
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException;
}
  