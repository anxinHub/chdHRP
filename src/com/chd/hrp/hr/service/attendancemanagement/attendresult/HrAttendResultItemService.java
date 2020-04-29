package com.chd.hrp.hr.service.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAttendResultItemService {

	String queryAttendResultItem(Map<String, Object> page) throws DataAccessException;

	String queryResultItemHead(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 考勤汇总打印
	 */ 
	public List<Map<String, Object>> queryAttendResultItemPrint(Map<String, Object> mapVo) throws DataAccessException;
}
