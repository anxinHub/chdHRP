package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccPermData;
import com.chd.hrp.acc.entity.AccUserPermData;

public interface AccPermDataMapper extends SqlMapper{
	public List<AccPermData> queryAccPermData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AccUserPermData> queryAccUserPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccUserPermData> queryAccRolePermData(Map<String,Object> entityMap) throws DataAccessException;

	public int addAccUserPermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addAccRolePermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteAccRolePermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteAccUserPermData(Map<String,Object> entityMap)throws DataAccessException;
}
