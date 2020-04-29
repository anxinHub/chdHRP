package com.chd.hrp.hr.service.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAttendResultSummaryService {
  
    /**
     * 汇总查询
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public String queryAttendResultSummary(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 考勤汇总打印
	 */
	public List<Map<String, Object>> printAttendSummary(Map<String, Object> mapVo) throws DataAccessException;
}
