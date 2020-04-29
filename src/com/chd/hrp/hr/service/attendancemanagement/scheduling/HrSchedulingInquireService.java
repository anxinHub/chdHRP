package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrSchedulingInquireService {
    //查询人员按科室
	String queryEmpPbByDept(Map<String, Object> page) throws DataAccessException;
    //查询表头月份
	List<Map<String, Object>> queryMonth(Map<String, Object> mapVo) throws DataAccessException;
	//查询人员排班按人员
	String queryEmpPb(Map<String, Object> page) throws DataAccessException;
	//查询排班名称
	String queryAllPB(Map<String, Object> mapVo)throws DataAccessException;

}
