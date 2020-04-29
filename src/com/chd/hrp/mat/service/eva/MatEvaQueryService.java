package com.chd.hrp.mat.service.eva;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatEvaQueryService {

	public String queryMatEvaReportMain(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMatEvaReportDetail(Map<String, Object> mapVo) throws DataAccessException;

	public String queryTargetCodeThead(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMatEvaTarget(Map<String, Object> mapVo) throws DataAccessException;
	
}
