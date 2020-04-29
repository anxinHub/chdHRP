package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccBudgAutoVouchService {
	
	//查询表头
	public String queryBudgAutoVouchHead(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询
	public String queryBudgAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryBudgAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据业务类型查询凭证Json
	public String queryVouchJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;
	
	public String saveAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	
}
