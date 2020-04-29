package com.chd.hrp.cost.dao.director;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostRevenueAnalysisMapper extends SqlMapper{

	 public List<Map<String, Object>> queryCostDeptRevenueConstitute(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueConstitute(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostDeptRevenueTrend(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueTrend(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostDeptRevenueTrendEcharts(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueTrendEcharts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostDeptRevenueCompare(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueCompare(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostDeptRevenueOpeningOrder(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueOpeningOrder(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostDeptRevenueImplement(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostDeptRevenueImplement(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 public List<Map<String, Object>> queryCostDeptRevenueConstitutePrint(Map<String,Object> entityMap) throws DataAccessException;
}
