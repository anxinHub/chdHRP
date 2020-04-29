package com.chd.hrp.htc.service.task.readydata.deptincome;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskProjectCostDeptIncomeService {
	
	 public String disposeHtcTaskProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcTaskProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
}
