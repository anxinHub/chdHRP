package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrCountersignMapper extends SqlMapper{
    /**
     * 审签 
     * @param saveList
     * @return
     */
	void auditCountersign(List<Map> saveList);

}
