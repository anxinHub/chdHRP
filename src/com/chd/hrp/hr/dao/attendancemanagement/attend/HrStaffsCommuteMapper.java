package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrStaffsCommute;

/**
 * 科室职工考勤
 * @author Administrator
 *
 */
public interface HrStaffsCommuteMapper extends SqlMapper{
    /**
     * 添加查询数据
     * @param entityMap
     * @return
     */
	List<HrStaffsCommute> queryStaffsCommuteById(Map<String, Object> entityMap);
    /**
     * 删除科室考勤
     * @param entityList
     */
	void deleteStaffsCommute(List<HrStaffsCommute> entityList);
	/**
	 * 审核科室职工考勤
	 * @param entityList
	 * @return
	 */
	String auditStaffsCommute(List<Map<String, Object>> entityList);
	/**
	 * 销审科室职工考勤
	 * @param entityList
	 * @return
	 */
	String reAuditStaffsCommute(List<Map<String, Object>> entityList);

}
