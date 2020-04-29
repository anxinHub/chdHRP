package com.chd.hrp.cost.service.director;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostRevenueAnalysisService {

	public String queryCostDeptRevenueConstitute(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDeptRevenueConstitutePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDeptRevenueTrend(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDeptRevenueTrendPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDeptRevenueTrendEcharts(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryCostDeptRevenueCompare(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDeptRevenueComparePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDeptRevenueOpeningOrder(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDeptRevenueOpeningOrderPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDeptRevenueImplement(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDeptRevenueImplementPrint(Map<String,Object> entityMap) throws DataAccessException;


	
}
