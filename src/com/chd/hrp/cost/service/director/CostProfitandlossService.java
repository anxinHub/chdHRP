package com.chd.hrp.cost.service.director;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostProfitandlossService {
	
	public String queryCostShare(Map<String, Object> entityMap)throws DataAccessException;
	
	public String querycostShareCost(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryCostShareCostDir(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryCostShareCostAss(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryCostShareCostMed(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryCostSharePrint(Map<String,Object> entityMap) throws DataAccessException;
}
