package com.chd.hrp.acc.service.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccUserPermData;


public interface AccPermDataService {
	public String queryAccPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccUserPermData> queryAccUserPermData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccUserPermData> queryAccRolePermData(Map<String,Object> entityMap) throws DataAccessException;

	public String addAccUserPermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String addAccRolePermData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String deleteAccRolePermData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteAccUserPermData(Map<String,Object> entityMap)throws DataAccessException;
}
