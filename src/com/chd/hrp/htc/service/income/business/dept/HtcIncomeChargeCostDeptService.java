package com.chd.hrp.htc.service.income.business.dept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeChargeCostDeptService {
	
	public String queryHtcIncomeChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
	
	public String collectHtcIncomeChargeCostDept(Map<String, Object> entityMap) throws DataAccessException;
}
