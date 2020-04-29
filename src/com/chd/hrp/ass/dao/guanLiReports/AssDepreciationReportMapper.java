package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 资产折旧报表
 * @author fhqfm
 * 
 */
public interface AssDepreciationReportMapper extends SqlMapper{
	
	List<Map<String, Object>> queryHosDeptKinds(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssTypeDicts(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryHosSources(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDepreciationAnalyse(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseAddPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseDelPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseAllTotal(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseAllTotalAddPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseAllTotalDelPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseMedicalCostTotal(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseMCostTotalAddPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssDepreciationAnalyseMCostTotalDelPrice(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
	List<Map<String, Object>> queryAssDepreciationAnalyseNotDepre(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDepreciationAnalyse(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssDepreciationSummary(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssDepreciationSummary(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssDepreciationInfo(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssDepreciationInfo(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssAnalyPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssDeprePrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssInfoPrint(Map<String, Object> entityMap);

}
