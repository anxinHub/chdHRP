package com.chd.hrp.htc.service.income.readydata.deptincome;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeProjectCostDeptIncomeService {
	
	 public String disposeHtcIncomeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcIncomeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
