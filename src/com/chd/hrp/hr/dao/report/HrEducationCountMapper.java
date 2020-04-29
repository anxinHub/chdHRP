package com.chd.hrp.hr.dao.report;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrEducationCountMapper extends SqlMapper{

	List<Map<String, Object>> queryHrEducationCount(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHrAgeCount(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHrWorkAgeCount(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHrUserCountMain(Map<String, Object> mapVo);

	List<Map<String, Object>> queryDegreeColumns(Map<String, Object> mapVo);

	List<Map<String, Object>> queryStationColumns(Map<String, Object> mapVo);

}
