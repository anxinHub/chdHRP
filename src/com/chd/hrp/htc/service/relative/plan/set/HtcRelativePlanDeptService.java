package com.chd.hrp.htc.service.relative.plan.set;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativePlanDeptService {
	
	public String addHtcRelativePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcRelativePlanDeptByPlanSetOk(Map<String,Object> entityMap)throws DataAccessException;
	  
   public String queryHtcRelativePlanDeptByPlanSetNO(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
