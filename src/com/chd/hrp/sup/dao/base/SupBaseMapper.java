package com.chd.hrp.sup.dao.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SupBaseMapper extends SqlMapper {

	/**
	 * @Description 材料字典列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatInvList(Map<String, Object> entityMap) throws DataAccessException;

	public List<?> queryMatInvList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 材料字典列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> querySupDict(Map<String, Object> entityMap) throws DataAccessException;

	public List<?> querySupDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public Date queryMatInvBatchInva(Map<String, Object> entityMap) throws DataAccessException;

	public Date queryMatInvBatchDisinfect(Map<String, Object> entityMap) throws DataAccessException;

	public Double queryMatInvBatchPrice(Map<String, Object> entityMap) throws DataAccessException;

	public Double queryMatAffiInvBatchPrice(Map<String, Object> entityMap) throws DataAccessException;
}
