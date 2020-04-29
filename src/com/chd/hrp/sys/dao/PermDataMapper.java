package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.PermData;
import com.chd.hrp.sys.entity.UserPermData;


public interface PermDataMapper extends SqlMapper{
	public List<PermData> queryPermData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<UserPermData> queryUserPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<UserPermData> queryRolePermData(Map<String,Object> entityMap) throws DataAccessException;

	public int addUserPermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addRolePermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteRolePermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteUserPermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryColumnIdByTableCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchUserPermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchRolePermData(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
