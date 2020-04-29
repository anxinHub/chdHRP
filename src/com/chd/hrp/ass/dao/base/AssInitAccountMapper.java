package com.chd.hrp.ass.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssInitAccountMapper extends SqlMapper{
	int addCardAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addResourceAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addShareDeptAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addShareDeptRAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addPayStageAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addFileAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addPhotoAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addDepreAccAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addDepreManageAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	int addAccessoryAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer selectInitCardExists(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer selectInitResourceExists(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer selectInitShareDeptExists(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer selectInitDepreAccExists(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer addCardInitFlag(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> querResourceValidate(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> querShareDeptValidate(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> querDepreAccValidate(Map<String,Object> entityMap)throws DataAccessException;
}
