package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrSequestrationService {

	String addSequestration(Map<String, Object> mapVo)throws DataAccessException;

	String UpdateSequestration(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询封存设置周
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String querySequestration(Map<String, Object> mapVo)throws DataAccessException;

}
