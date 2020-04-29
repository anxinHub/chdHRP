package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HrCycleMapper extends SqlMapper{
	
	public Map<String,Object> queryHrCycle(Map<String,Object> entityMap) throws DataAccessException;

	public Map<String,Object> queryHrCycleIsExistByYear(Map<String, Object> entityMap) throws DataAccessException;

	


}
