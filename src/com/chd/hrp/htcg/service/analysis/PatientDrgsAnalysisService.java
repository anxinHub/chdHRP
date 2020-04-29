package com.chd.hrp.htcg.service.analysis;

import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface PatientDrgsAnalysisService {
	
	
	/*
	 * 单病种成本明细表
	 */
	public String queryPatientDrgsAnalysisCostDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 单病种成本表
	 */
	public String queryPatientDrgsAnalysisCost(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
