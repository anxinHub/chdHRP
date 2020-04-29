package com.chd.hrp.med.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedAdviceRefOut;

public interface MedAdviceRefOutService {
	
	public String addMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMedAdviceRefOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
