package com.chd.hrp.mat.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatSupCertWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSupCertWarning(Map<String,Object> entityMap)throws DataAccessException;
}
