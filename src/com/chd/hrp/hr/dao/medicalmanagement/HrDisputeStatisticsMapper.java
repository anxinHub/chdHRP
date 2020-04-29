package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrDisputeStatisticsMapper extends SqlMapper{

	List<Map<String, Object>> queryDisputeStatisticsByPrint(Map<String, Object> entityMap);


}
