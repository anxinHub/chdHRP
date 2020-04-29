package com.chd.hrp.mat.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatBatchWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatBatchWarning(Map<String,Object> entityMap)throws DataAccessException;
}
