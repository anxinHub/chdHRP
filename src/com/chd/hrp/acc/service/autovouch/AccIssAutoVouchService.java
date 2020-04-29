package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccIssAutoVouchService {
	// 查询表头
	public String queryIssAutoVouchHead(Map<String, Object> entityMap) throws DataAccessException;

	// 查询
	public String queryIssAutoVouch(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryIssAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;

	// 根据业务类型查询凭证Json
	public String queryVouchJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;
}
