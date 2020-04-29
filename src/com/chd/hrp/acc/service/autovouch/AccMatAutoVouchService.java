package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccMatAutoVouchService {
	
	//查询表头
	public String queryMatAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryMatAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据业务类型查询凭证Json
	public String queryVouchJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String saveAutoVouch(Map<String, Object> map)throws DataAccessException;

	String checkAutoVouch(Map<String, Object> map) throws DataAccessException;
	
	public String getLeftJoinSql(String main, String main_key, String detail, String where_sql) throws DataAccessException;
}
