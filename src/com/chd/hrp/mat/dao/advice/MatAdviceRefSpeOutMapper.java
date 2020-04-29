package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAdviceRefOut;

public interface MatAdviceRefSpeOutMapper extends SqlMapper{
	
	public int addMatAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefSpeOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatAdviceRefOut> queryMatAdviceRefSpeOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefSpeOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdviceRefOut queryMatAdviceRefSpeOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
