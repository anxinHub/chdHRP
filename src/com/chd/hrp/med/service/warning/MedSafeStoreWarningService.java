package com.chd.hrp.med.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MedSafeStoreWarningService {
	/**
	 * 查询安全库存预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedSafeStoreWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 超高限预警 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedHighStoreWarning(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 短缺货预警 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedShortStoreWarning(Map<String, Object> page) throws DataAccessException;
}
