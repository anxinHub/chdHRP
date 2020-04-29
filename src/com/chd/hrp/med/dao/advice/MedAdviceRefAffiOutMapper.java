package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAdviceRefOut;

public interface MedAdviceRefAffiOutMapper extends SqlMapper{
	
	public int addMedAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedAdviceRefAffiOut(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedAdviceRefAffiOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefAffiOut(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedAdviceRefOut> queryMedAdviceRefAffiOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefAffiOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedAdviceRefOut queryMedAdviceRefAffiOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
