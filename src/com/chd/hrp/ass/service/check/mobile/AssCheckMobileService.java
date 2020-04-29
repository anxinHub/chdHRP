package com.chd.hrp.ass.service.check.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssCheckMobileService extends SqlService {

	/**
	 * @Description 同步盘点数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String generate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 同步报修数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String repairsGenerate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 同步巡检数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String pollingGenerate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 同步保养数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String maintainGenerate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 同步计量数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String measureGenerate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 移动盘点数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMoblieContrast(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 盘点单数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryCheckContrast(Map<String, Object> entityMap) throws DataAccessException;
}
