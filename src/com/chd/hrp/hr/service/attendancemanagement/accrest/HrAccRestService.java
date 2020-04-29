package com.chd.hrp.hr.service.attendancemanagement.accrest;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAccRestService {
    /**
     * 查询积休明细
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryAccRest(Map<String, Object> page) throws DataAccessException;

}
