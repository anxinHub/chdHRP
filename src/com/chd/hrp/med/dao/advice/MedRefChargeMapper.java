package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRefCharge;

public interface MedRefChargeMapper extends SqlMapper{
	public int addMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedRefCharge> queryMedRefCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedRefCharge> queryMedRefCharge(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedRefCharge queryMedRefChargeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedRefCharge queryMedRefChargeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
