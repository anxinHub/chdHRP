package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAdvice;

public interface MatAdviceMapper extends SqlMapper{
	public int addMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBatchMatAdviceByHxFlag(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatAdvice> queryMatAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatAdvice> queryMatAdvice(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatAdvice queryMatAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatAdvice queryMatAdviceByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<MatAdvice> queryMatAdviceByInit(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatAdvice> queryMatAdviceByInitDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public int queryMatOutDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int addMatOutBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
}
