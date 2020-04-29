package com.chd.hrp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.UserPermData;


public interface PermQueryService {
	public String queryPermQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryUserPermQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryRolePermQuery(Map<String,Object> entityMap) throws DataAccessException;

	public String addUserPermQuery(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String addRolePermQuery(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String deleteRolePermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteUserPermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryColumnIdByTableCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchUserPermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchRolePermQuery(Map<String,Object> entityMap)throws DataAccessException;
}
