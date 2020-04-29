package com.chd.hrp.hr.service.attendancemanagement.attend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrCycleService {

	String addCycle(Map<String, Object> mapVo);
	
	//查询考勤周期
	public String queryHrCycle(Map<String,Object> entityMap) throws DataAccessException;
	//生成
	public String createCycle(Map<String, Object> entityMap) throws DataAccessException;
	//考勤期间
	public String queryHrPeriod(Map<String, Object> mapVo) throws DataAccessException;
    //清除
	public	String deleteCycle(Map<String, Object> mapVo) throws DataAccessException;
}
