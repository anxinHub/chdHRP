package com.chd.hrp.htc.service.income.plan.set;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;

public interface HtcIncomePlanSetService {
	
	public String addHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcIncomePlanSet queryHtcIncomePlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcIncomePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String queryHtcIncomePlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
	
	public String auditHtcIncomePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String cancelAuditIncomeHtcPlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryHtcIncomePlanHistory(Map<String,Object> entityMap)throws DataAccessException;
	
}
