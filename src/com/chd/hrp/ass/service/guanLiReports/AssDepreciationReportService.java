package com.chd.hrp.ass.service.guanLiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 资产折旧报表
 * @author fhqfm
 *
 */
public interface AssDepreciationReportService {

	/**
	 * 资产折旧分析
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssDepreciationAnalyse(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 资产折旧汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssDepreciationSummary(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 资产折旧信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssDepreciationInfo(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 资产分类表头
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssTypeHead(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
	
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssDeprePrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssInfoPrint(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryAssDepreciationAnalysePrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
