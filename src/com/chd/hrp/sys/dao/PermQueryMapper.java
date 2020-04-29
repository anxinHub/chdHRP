package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.PermQuery;
import com.chd.hrp.sys.entity.UserPermData;


public interface PermQueryMapper extends SqlMapper{
	public List<PermQuery> queryPermQuery(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<UserPermData> queryUserPermQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryRolePermQuery(Map<String,Object> entityMap) throws DataAccessException;

	public int addUserPermQuery(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addRolePermQuery(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteRolePermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteUserPermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryColumnIdByTableCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchUserPermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchRolePermQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
