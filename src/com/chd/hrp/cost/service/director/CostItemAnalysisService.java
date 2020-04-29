package com.chd.hrp.cost.service.director;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostItemAnalysisService {

	public String queryCostProjectTrend(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostProjectTrendPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostProjectTrendEcharts(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryCostProjectCompare(Map<String, Object> entityMap)throws DataAccessException;
	
	public  List<Map<String,Object>> queryCostProjectComparePrint(Map<String, Object> entityMap)throws DataAccessException;


}
