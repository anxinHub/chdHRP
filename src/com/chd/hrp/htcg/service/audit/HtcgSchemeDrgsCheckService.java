package com.chd.hrp.htcg.service.audit;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcgSchemeDrgsCheckService {

	public String queryHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public String auditHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;

	public String reAuditHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	public String flagHtcgSchemeDrgsCheckDrgsCheck(Map<String, Object> entityMap)throws DataAccessException;
	
	public String querySchemeDrgsCheckReport(Map<String, Object> entityMap) throws DataAccessException;


}
