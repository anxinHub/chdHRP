package com.chd.hrp.med.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedRefCharge;

public interface MedRefChargeService {
	public String addMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMedRefCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MedRefCharge queryMedRefChargeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedRefCharge queryMedRefChargeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
