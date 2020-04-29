package com.chd.hrp.hr.service.report;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface HrEducationCountService  extends SqlService{

	String queryHrEducationCount(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHrEducationCountPrint(Map<String, Object> mapVo);

	String queryHrAgeCount(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryHrAgeCountPrint(Map<String, Object> mapVo);

	String queryHrWorkAgeCount(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryHrWorkAgeCountPrint(Map<String, Object> mapVo);

	String queryHrUserCountMain(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryHrUserCountMainPrint(Map<String, Object> mapVo);

	Map<String,Object> queryDegreeColumns(Map<String, Object> mapVo);


}
