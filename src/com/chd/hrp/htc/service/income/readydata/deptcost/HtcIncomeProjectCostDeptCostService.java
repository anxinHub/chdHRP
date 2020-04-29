package com.chd.hrp.htc.service.income.readydata.deptcost;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeProjectCostDeptCostService {
	
	 public String disposeHtcIncomeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcIncomeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
}
