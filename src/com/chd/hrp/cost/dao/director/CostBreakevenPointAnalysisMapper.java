package com.chd.hrp.cost.dao.director;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostBreakevenPointAnalysisMapper extends SqlMapper{

	 public List<Map<String, Object>> queryCostBreakevenPointClinic(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointClinic(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 public List<Map<String, Object>> queryCostBreakevenPointInhos(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointInhos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostBreakevenPointMedical(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointMedical(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostBreakevenPointClinicCalculation(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointClinicCalculation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 public List<Map<String, Object>> queryCostBreakevenPointInhosCalculation(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointInhosCalculation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	 public List<Map<String, Object>> queryCostBreakevenPointMedicalCalculation(Map<String,Object> entityMap) throws DataAccessException;
		
	 public List<Map<String, Object>> queryCostBreakevenPointMedicalCalculation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 public List<Map<String, Object>> queryCostBreakevenPointClinicPrint(Map<String, Object> entityMap)throws DataAccessException;
     
	 public List<Map<String, Object>> queryCostBreakevenPointInhosPrint(Map<String, Object> entityMap)throws DataAccessException;
	 
}
