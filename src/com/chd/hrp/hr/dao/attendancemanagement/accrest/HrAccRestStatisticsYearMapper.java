package com.chd.hrp.hr.dao.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrAccRestStatisticsYearMapper extends SqlMapper{

	List<Map<String, Object>> query(Map<String, Object> entityMap);

	List<Map<String, Object>> overtimeYearQuery(Map<String, Object> entityMap);

	List<Map<String, Object>> overtimeYearQuery(Map<String, Object> entityMap,
			RowBounds rowBounds);

	List<Map<String, Object>> applyingLeavesQueryYear(
			Map<String, Object> entityMap);

	List<Map<String, Object>> applyingLeavesQueryYear(
			Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryByPrint(Map<String, Object> entityMap);

}
