package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAdviceRefOut;

public interface MatAdviceRefOutMapper extends SqlMapper{
	
	public int addMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
