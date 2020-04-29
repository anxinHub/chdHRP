package com.chd.hrp.mat.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatAdviceRefOut;

public interface MatAdviceRefOutService {
	
	public String addMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMatAdviceRefOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
