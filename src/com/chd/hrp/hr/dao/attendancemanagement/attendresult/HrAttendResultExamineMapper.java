package com.chd.hrp.hr.dao.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrAttendResultExamineMapper extends SqlMapper{

	int existsByState(Map<String, Object> entityMap);

	void updateStateBySubmit(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAttendExamine(Map<String, Object> entityMap, RowBounds rowBounds);

	String printAttendExamine(Map<String, Object> entityMap);

	String summaryAttendExamine(Map<String, Object> entityMap);

	Map<String, Object> checkOrUnCheckResultExamine(
			Map<String, Object> entityMap);

	Map<String, Object> confirmAttendExamine(Map<String, Object> entityMap);


	void updateStateByCheck(Map<String, Object> entityMap);

	void deleteAttendExamineResult(Map<String, Object> entityMap);

}
