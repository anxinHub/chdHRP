package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAdviceRefOut;

public interface MatAdviceRefAffiOutMapper extends SqlMapper{
	
	public int addMatAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefAffiOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefAffiOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefAffiOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefAffiOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
