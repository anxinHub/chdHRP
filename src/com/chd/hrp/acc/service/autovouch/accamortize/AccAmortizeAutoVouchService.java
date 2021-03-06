package com.chd.hrp.acc.service.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccAmortizeAutoVouchService {
	// 查询表头
	public String queryAmortizeAutoVouchHead(Map<String, Object> entityMap) throws DataAccessException;

	// 查询
	public String queryAmortizeAutoVouch(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAmortizeAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException;

	// 根据业务类型查询凭证Json
	public String queryVouchJsonByBusi(Map<String, Object> entityMap) throws DataAccessException;

}
