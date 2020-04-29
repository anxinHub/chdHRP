package com.chd.hrp.hr.dao.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrAttendResultSummaryMapper extends SqlMapper{

	List<Map<String, Object>> queryAttendResultSummary(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryprintAttendSummary(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryItemList(Map<String, Object> entityMap);

}
