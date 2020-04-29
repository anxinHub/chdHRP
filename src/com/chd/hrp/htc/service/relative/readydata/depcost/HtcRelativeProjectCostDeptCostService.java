package com.chd.hrp.htc.service.relative.readydata.depcost;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeProjectCostDeptCostService {
	
	 public String disposeHtcRelativeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcRelativeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
}
