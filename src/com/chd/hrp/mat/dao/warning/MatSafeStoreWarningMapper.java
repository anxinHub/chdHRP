package com.chd.hrp.mat.dao.warning;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatSafeStoreWarningMapper  extends SqlMapper{
	
	/**
	 * 查询安全库存预警 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatSafeStoreWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询安全库存预警 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSafeStoreWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 超高限预警 查询 分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatHighStoreWarning(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 超高限预警 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatHighStoreWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 短缺货预警 查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatShortStoreWarning(	Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 短缺货预警 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatShortStoreWarning(	Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException; 
}
