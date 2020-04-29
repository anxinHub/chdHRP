package com.chd.hrp.ass.service.biandongmonthly;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssMonthlyReportDeptService {

	String queryAssMonthlyReportDept(Map<String, Object> entityMap)throws DataAccessException;
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssMonthlyReportMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
