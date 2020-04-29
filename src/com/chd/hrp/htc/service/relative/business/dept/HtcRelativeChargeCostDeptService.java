package com.chd.hrp.htc.service.relative.business.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeChargeCostDeptService {
	
	public String queryHtcRelativeChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
	
	public String collectHtcRelativeChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
}
