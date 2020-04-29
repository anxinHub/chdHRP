package com.chd.hrp.htc.service.task.business.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskChargeCostDeptService {

	public String queryHtcTaskChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
	
	public String collectHtcTaskChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
}
