package com.chd.hrp.med.dao.warning;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedSafeStoreWarningMapper  extends SqlMapper{
	
	/**
	 * 查询安全库存预警 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedSafeStoreWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询安全库存预警 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedSafeStoreWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 超高限预警 查询 分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedHighStoreWarning(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 超高限预警 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedHighStoreWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 短缺货预警 查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedShortStoreWarning(	Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 短缺货预警 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedShortStoreWarning(	Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException; 
}
