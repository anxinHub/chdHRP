package com.chd.hrp.cost.service.director;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostBreakevenPointAnalysisService {

	public String queryCostBreakevenPointClinic(Map<String, Object> entityMap)throws DataAccessException;
	
    public String queryCostBreakevenPointInhos(Map<String,Object> entityMap) throws DataAccessException;
    
    public String queryCostBreakevenPointMedical(Map<String,Object> entityMap) throws DataAccessException;
    
    public String queryCostBreakevenPointClinicCalculation(Map<String, Object> entityMap)throws DataAccessException;
	
    public String queryCostBreakevenPointInhosCalculation(Map<String,Object> entityMap) throws DataAccessException;
    
    public String queryCostBreakevenPointMedicalCalculation(Map<String,Object> entityMap) throws DataAccessException;

    public List<Map<String,Object>> queryCostBreakevenPointClinicPrint(Map<String,Object> entityMap) throws DataAccessException;
    
    public List<Map<String,Object>> queryCostBreakevenPointInhosPrint(Map<String,Object> entityMap) throws DataAccessException;
    
}
