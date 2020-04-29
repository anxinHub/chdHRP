package com.chd.hrp.mat.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatSafeStoreWarningService {
	/**
	 * 查询安全库存预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSafeStoreWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 超高限预警 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatHighStoreWarning(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 短缺货预警 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatShortStoreWarning(Map<String, Object> page) throws DataAccessException;
}
