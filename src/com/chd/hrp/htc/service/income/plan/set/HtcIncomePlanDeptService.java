package com.chd.hrp.htc.service.income.plan.set;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomePlanDeptService {
	
	public String addHtcIncomePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcIncomePlanDeptByPlanSetOk(Map<String,Object> entityMap)throws DataAccessException;
	  
   public String queryHtcIncomePlanDeptByPlanSetNO(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
