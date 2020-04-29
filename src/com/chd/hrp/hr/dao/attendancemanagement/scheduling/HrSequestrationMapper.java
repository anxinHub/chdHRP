package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrSequestrationMapper extends SqlMapper{

	void addMonth(Map<String, Object> entityMap);

	void deleteMonth(Map<String, Object> entityMap);
    /**
     * 查询
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> querySequestration(Map<String, Object> entityMap);
    /**
     * 查询排班封存月
     * @param entityMap
     * @return
     */
	Map<String, Object> querySequestrationM(Map<String, Object> entityMap);

}
