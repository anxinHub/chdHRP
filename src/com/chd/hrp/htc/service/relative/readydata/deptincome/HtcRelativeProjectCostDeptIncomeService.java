package com.chd.hrp.htc.service.relative.readydata.deptincome;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeProjectCostDeptIncomeService {
	
	 public String disposeHtcRelativeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
		
	 public String queryHtcRelativeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
