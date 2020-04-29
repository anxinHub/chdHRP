package com.chd.hrp.ass.dao.biandongmonthly;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssMonthlyReportDeptMapper extends SqlMapper{

	List<Map<String, Object>> queryPaySearch(Map<String, Object> entityMap,
			RowBounds rowBounds);

	List<Map<String, Object>> queryPaySearch(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssMonthlyReportMainPrint(
			Map<String, Object> entityMap);

}
