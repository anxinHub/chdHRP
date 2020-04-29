package com.chd.hrp.mat.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatRefStoreDept;

public interface MatRefStoreDeptService {
	public String addMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMatRefStoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryMatRefStoreDeptByStore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MatRefStoreDept queryMatRefStoreDeptOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatRefStoreDept queryMatRefStoreDeptOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;	
}
