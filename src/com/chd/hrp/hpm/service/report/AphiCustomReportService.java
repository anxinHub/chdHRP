package com.chd.hrp.hpm.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiCustomReportService {
	
	/**
	 * @Description 
	 * <BR>查询指标
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*//*
	public String queryCustomTargetTree(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * @Description 
	 * <BR>查询科室
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*//*
	public String queryCustomDeptTree(Map<String, Object> entityMap) throws DataAccessException;*/
	
	/**
	 * @Description 
	 * <BR>查询表头
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryHpmCustomReportHead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>主查询
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryHpmCustomReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>自定义指标表 查询-打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryHpmCustomReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>职能科室考核明细表 查询-打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryHpmDeptTargetCkeckReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>财务其它考核明细表 查询-打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHpmOtherTargetCkeckReportPrint(Map<String, Object> entityMap) throws DataAccessException;
}
