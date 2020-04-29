package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRefStoreDept;

public interface MatRefStoreDeptMapper extends SqlMapper{
	public int addMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatRefStoreDept(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatRefStoreDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatRefStoreDept> queryMatRefStoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatRefStoreDept> queryMatRefStoreDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<MatRefStoreDept> queryMatRefStoreDeptByStore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatRefStoreDept> queryMatRefStoreDeptByStore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatRefStoreDept queryMatRefStoreDeptOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatRefStoreDept queryMatRefStoreDeptOutByUniqueness(Map<String,Object> entityMap)throws DataAccessException;	
}
