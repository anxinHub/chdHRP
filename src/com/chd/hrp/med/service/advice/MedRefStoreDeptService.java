package com.chd.hrp.med.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedRefStoreDept;

public interface MedRefStoreDeptService {
	public String addMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMedRefStoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryMedRefStoreDeptByStore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MedRefStoreDept queryMedRefStoreDeptOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedRefStoreDept queryMedRefStoreDeptOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;	
}
