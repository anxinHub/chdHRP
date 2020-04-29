package com.chd.hrp.htc.service.task.readydata.deptcost;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskProjectCostDeptCostService {
	
	 public String disposeHtcTaskProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcTaskProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
}
