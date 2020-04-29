package com.chd.hrp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.UserPermData;


public interface PermDataService {
	public String queryPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryUserPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryRolePermData(Map<String,Object> entityMap) throws DataAccessException;

	public String addUserPermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String addRolePermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String deleteRolePermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteUserPermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryColumnIdByTableCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchUserPermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchRolePermData(Map<String,Object> entityMap)throws DataAccessException;

	String addBatchPerm(Map<String, Object> entityMap)
			throws DataAccessException;
}
