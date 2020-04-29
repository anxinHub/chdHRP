package com.chd.hrp.hpm.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiNatureReportService {
	
	/**
	 * @Description 
	 * <BR>查询指标
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryTargetTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>查询报表
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryHpmNatureReport(Map<String, Object> entityMap) throws DataAccessException;
}
