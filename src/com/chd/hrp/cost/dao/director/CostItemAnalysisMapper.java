package com.chd.hrp.cost.dao.director;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostItemAnalysisMapper extends SqlMapper{

	 public List<Map<String, Object>> queryCostProjectTrend(Map<String,Object> entityMap) throws DataAccessException;
	
	 public List<Map<String, Object>> queryCostProjectTrend(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostProjectTrendEcharts(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostProjectTrendEcharts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostProjectCompare(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostProjectCompare(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 public List<Map<String, Object>> queryCostProjectTrendPrint(Map<String,Object> entityMap) throws DataAccessException;
}
