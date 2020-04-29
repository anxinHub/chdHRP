package com.chd.hrp.mat.service.storage.traceability;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatInvTraceabilityService {

	/**
	 * 左边查询条形码等等
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryMatInvTraceability(Map<String, Object> page)throws DataAccessException;

	String queryMatInvTraceabilityBar_code(Map<String, Object> mapVo) throws DataAccessException;  

}
