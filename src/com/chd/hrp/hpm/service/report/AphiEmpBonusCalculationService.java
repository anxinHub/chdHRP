package com.chd.hrp.hpm.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/*
 * 职工绩效工资核算计算表
 * */
public interface AphiEmpBonusCalculationService {
	
	public String queryEmpBonusCalculationByReport(Map<String, Object> entityMap)	throws DataAccessException;
	
	//查询动态表头
	public String queryEmpBonusCalculationGrid(Map<String, Object> entityMap)	throws DataAccessException;
	
	public List<Map<String,Object>> queryEmpBonusCalculationByReportPrint(Map<String, Object> entityMap)	throws DataAccessException;
}
