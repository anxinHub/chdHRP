package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClass;

public interface HrAttendClassMapper extends SqlMapper{

	void deleteAttendClass(List<HrAttendClass> alllistVo);
    /**
     * 查询班次区域
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryAttendAreacode(Map<String, Object> entityMap);
	
	/**
	 * 查询是否被引用
	 * @param hrAttendArea
	 * @return
	 */
	int queryAttendScheduling(HrAttendClass hrAttendClass);
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */
	List<HrAttendClass> queryByCodeClass(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendClassByPrint(Map<String, Object> entityMap);
	List<HrAttendClass> queryByCodeClassUpdate(Map<String, Object> entityMap);

}
