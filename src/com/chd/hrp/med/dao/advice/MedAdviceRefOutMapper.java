package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAdviceRefOut;

public interface MedAdviceRefOutMapper extends SqlMapper{
	
	public int addMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedAdviceRefOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedAdviceRefOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
