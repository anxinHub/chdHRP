package com.chd.hrp.mat.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatAdvice;

public interface MatAdviceService {
	public String addMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMatAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MatAdvice queryMatAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdvice queryMatAdviceByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
}
