package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAdvice;

public interface MedAdviceMapper extends SqlMapper{
	public int addMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBatchMedAdviceByHxFlag(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedAdvice(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedAdvice> queryMedAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedAdvice> queryMedAdvice(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedAdvice queryMedAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdvice queryMedAdviceByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<MedAdvice> queryMedAdviceByInit(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedAdvice> queryMedAdviceByInitDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public int queryMedOutDetailNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int addMedOutBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryMedAdvicePrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryMedAdvicePrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
}
