package com.chd.hrp.hr.dao.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrAttendResultItemMapper  extends SqlMapper{

	List<Map<String, Object>> queryAttendResultItem(
			Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryResultItemHead(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAttendResultItem(
			Map<String, Object> entityMap);

}
