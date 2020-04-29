package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRefStoreDept;

public interface MedRefStoreDeptMapper extends SqlMapper{
	public int addMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMedRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMedRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MedRefStoreDept> queryMedRefStoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedRefStoreDept> queryMedRefStoreDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<MedRefStoreDept> queryMedRefStoreDeptByStore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MedRefStoreDept> queryMedRefStoreDeptByStore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MedRefStoreDept queryMedRefStoreDeptOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MedRefStoreDept queryMedRefStoreDeptOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;	
}
