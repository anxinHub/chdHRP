package com.chd.hrp.htc.service.relative.plan.set;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.relative.plan.set.HtcRelativePlanSet;

public interface HtcRelativePlanSetService {
	
	public String addHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcRelativePlanSet queryHtcRelativePlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String queryHtcRelativePlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
	
	public String auditHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String cancelAuditHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryHtcRelativePlanHistory(Map<String,Object> entityMap)throws DataAccessException;
	
}
