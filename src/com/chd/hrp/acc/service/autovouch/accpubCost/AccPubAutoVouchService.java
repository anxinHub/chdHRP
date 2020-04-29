package com.chd.hrp.acc.service.autovouch.accpubCost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccPubAutoVouchService {
	
	//查询表头
	public String queryPubAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryPubAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryPubAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String saveAutoVouch(Map<String, Object> map)throws DataAccessException;

	
	public String getLeftJoinSql(String main, String main_key, String detail, String where_sql) throws DataAccessException;
}
