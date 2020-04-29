package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAdviceRefOut;

public interface MedAdviceRefSpeOutMapper extends SqlMapper{
	
	public int addMedAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedAdviceRefSpeOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedAdviceRefSpeOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefSpeOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefSpeOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefSpeOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefSpeOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
