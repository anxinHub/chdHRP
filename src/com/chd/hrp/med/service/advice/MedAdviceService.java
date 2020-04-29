package com.chd.hrp.med.service.advice;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedAdvice;

public interface MedAdviceService {
	public String addMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMedAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MedAdvice queryMedAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdvice queryMedAdviceByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public Map<String,Object> queryMedAdviceByPrintTemlateNewPrint(Map<String,Object> entityMap)throws DataAccessException;
}
