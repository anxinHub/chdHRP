package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrCountersignService {
   /**
    * 审签
    * @param saveList
    * @return
    */
	String auditCountersign(List<Map<String,Object>> saveList) throws DataAccessException;

  String queryCountersign(Map<String, Object> page)throws DataAccessException;

}
