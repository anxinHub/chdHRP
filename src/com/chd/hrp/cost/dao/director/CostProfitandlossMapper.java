package com.chd.hrp.cost.dao.director;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostProfitandlossMapper extends SqlMapper{

	 public List<Map<String, Object>> queryCostShare(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostShare(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> querycostShareCost(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> querycostShareCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostShareCostDir(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostShareCostDir(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostShareCostAss(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostShareCostAss(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostShareCostMed(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostShareCostMed(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
     
	 public List<Map<String, Object>> queryCostSharePrint(Map<String, Object> entityMap)throws DataAccessException;
	 
}
