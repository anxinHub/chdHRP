package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrSchedulingInquireMapper extends SqlMapper {
    /**
     * 查询排班不带分页按科室
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryDept(Map<String, Object> entityMap);
    /**
     * 查询排班带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	List<Map<String, Object>> queryDept(Map<String, Object> entityMap,RowBounds rowBounds);
	List<Map<String, Object>> queryMonth(Map<String, Object> entityMap);
	/**
	 * 查询人员排班按人员按科室
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<Map<String, Object>> queryEmpPb(Map<String, Object> entityMap,RowBounds rowBounds);
	List<Map<String, Object>> queryEmpPb(Map<String, Object> entityMap);
	//查询排班名称
	List<Map<String, Object>> queryAllPB(Map<String, Object> entityMap);

}
