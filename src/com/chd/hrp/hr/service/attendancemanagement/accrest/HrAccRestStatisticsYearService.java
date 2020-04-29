package com.chd.hrp.hr.service.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HrAccRestStatisticsYearService {

	String queryAccRestStatisticsYear(Map<String, Object> page) throws DataAccessException;

	String overtimeYearQuery(Map<String, Object> page) throws DataAccessException;

	String applyingLeavesYearQuery(Map<String, Object> page) throws DataAccessException;

	String updateAccRestStatisticsYear(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAccRestStatisticsYearPrint(Map<String, Object> entityMap) throws DataAccessException; 
}
