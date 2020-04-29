package com.chd.hrp.acc.service.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccCoopAutoVouchService {
	
	//查询表头
	public String queryCoopAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryCoopAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCoopAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String saveAutoVouch(Map<String, Object> map)throws DataAccessException;

	
	public String getLeftJoinSql(String main, String main_key, String detail, String where_sql) throws DataAccessException;
}
